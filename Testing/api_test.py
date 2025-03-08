import requests
# Import the necessary module
from dotenv import load_dotenv
import os

# Load environment variables from the .env file (if present)
load_dotenv()

# Access environment variables as if they came from the actual environment
YT_KEY = os.getenv('YT_KEY')
DATABASE_URL = os.getenv('DATABASE_URL')

# 
endpoint = DATABASE_URL + 'search?'
query = 'Where Blue Light BLooms Origami Angel'
PARAMS = {'part':'snippet','q':query,'key':YT_KEY}

# Example usage
print(f'YT_KEY: {YT_KEY}')
print(f'DATABASE_URL: {DATABASE_URL}')
r = requests.get(url = endpoint, params = PARAMS)
data = r.json()
print(r)
print(data)
print(data['items'][0]['snippet']['title'])