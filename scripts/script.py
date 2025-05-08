from flask import Flask, request, jsonify
from flask_cors import CORS
import os
import json
import unittest
import sys
import socket
import re
import google.generativeai as genai

app = Flask(__name__)
CORS(app)

# Configure Google Gemini API
genai.configure(api_key="AIzaSyDMUSbeGGwlL2A7IpESVD8ErqW50oFkyVM")

# Use Gemini model
model = genai.GenerativeModel("gemini-2.0-flash")

def query_gemini(prompt):
    try:
        response = model.generate_content(prompt)
        # return json.loads(str.replace(str.replace(response.text, '```json', ''), '```', ''))
        return extract_json(response.text)
    except Exception as e:
        return {"error": f"Error generating Gemini response: {str(e)}"}

def extract_json(text):
    # First attempt: direct parse
    try:
        return json.loads(text)
    except json.JSONDecodeError:
        pass

    # Second attempt: strip markdown blocks
    # e.g. ```json\n{...}\n```
    md_match = re.search(r"```(?:json)?\s*({.*?})\s*```", text, re.DOTALL)
    if md_match:
        try:
            return json.loads(md_match.group(1))
        except json.JSONDecodeError:
            pass

    # Third attempt: find first valid JSON-like object
    brace_match = re.findall(r"(\{.*?\})", text, re.DOTALL)
    for match in brace_match:
        try:
            return json.loads(match)
        except json.JSONDecodeError:
            continue

    # Final fallback
    return {
        "error": "Could not parse valid JSON from model response.",
        "raw_response": text.strip()[:1000]  # limit to 1000 chars for safety
    }

# Build prompt based on input
def build_prompt(credit_usages, benefits):
    return (
        "You are given two input lists:\n"
        f"1. Credit card expenses: {credit_usages}\n"
        f"2. Available benefits: {benefits}\n\n"
        "Your task is to identify benefits that were likely **missed opportunities**, meaning:\n"
        "- The benefit `title` does NOT exactly match any `merchantName` in the expenses.\n"
        "- However, if a benefit belongs to the same **category** as any expense, and was not used, consider it a missed opportunity.\n\n"
        "Return a single JSON object with this exact format:\n"
        "{\n"
        "  \"benefitIds\": [\"<string>\", \"<string>\", ...]\n"
        "}\n\n"
        "⚠️ Use the value from `_id.$oid` of each benefit as the string ID.\n"
        "⚠️ Do NOT return any duplicate IDs.\n"
        "⚠️ Each ID should appear only **once**, even if matched by both title and category.\n"
        "⚠️ Do NOT include any explanation or formatting, only valid JSON.\n"
    )

@app.route('/analyze', methods=['POST'])
def analyze():
    try:
        data = request.get_json()
        credit_usages = data.get("credit_usages", [])
        benefits = data.get("benefits", [])

        prompt = build_prompt(credit_usages, benefits)
        gemini_response = query_gemini(prompt)

        return jsonify(gemini_response)
    except Exception as e:
        return jsonify({"error": str(e)}), 500

class TestCreditInsightsService(unittest.TestCase):

    def test_build_prompt_structure(self):
        benefits = [{'business': 'Zara', 'description': '10% off on purchases over $100'}, {'business': 'Fox', 'description': '10% off on purchases over $100'}, {'business': 'IKEA', 'description': '5% cashback for members'}]
        expenses = [{'amount': 25, 'date': '2025-03-30', 'merchant': 'Starbucks'}, {'amount': 150, 'date': '2025-03-28', 'merchant': 'Zara'}, {'amount': 150, 'date': '2025-03-28', 'merchant': 'Fox'}]
        prompt = build_prompt(expenses, benefits)
        self.assertIn("Credit card expenses", prompt)
        self.assertIn("Available benefits", prompt)
        self.assertIn("insights_by_category", prompt)

    def test_query_gemini_mock_response_structure(self):
        response = query_gemini("{'insights_by_category': [{'benefits': [{'business': 'Zara', 'description': '10% off on purchases over $100'}, {'business': 'Fox', 'description': '10% off on purchases over $100'}], 'category': 'Fashion'}]}")
        self.assertIsInstance(response, dict)

# Check if port is available
def is_port_in_use(port):
    try:
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            return s.connect_ex(('127.0.0.1', port)) == 0
    except Exception:
        return False

if __name__ == '__main__':
    run_tests = os.getenv("RUN_TESTS") == "1"

    if run_tests:
        unittest.main(argv=[sys.argv[0]])
    else:
        try:
            port = int(os.getenv("PORT", 5000))
            max_tries = 10
            tries = 0
            while is_port_in_use(port) and tries < max_tries:
                print(f"Port {port} in use. Trying port {port + 1}...")
                port += 1
                tries += 1

            if tries == max_tries:
                print("Error: No available ports found.")
                sys.exit(1)

            print(f"Starting server on http://localhost:{port}")
            app.run(host='0.0.0.0', port=port, debug=False)

        except Exception as e:
            print(f"Failed to start server: {e}")
            sys.exit(1)
