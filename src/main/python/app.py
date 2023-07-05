from flask import Flask,render_template, redirect,request
import requests
import wave
import io
import tempfile
import base64

from transcribe import transcribe_audio
from sympy.core.tests import test_truediv
#from builtins import id


app = Flask(__name__)

@app.route('/getId', methods=['POST']) # "/getId" is under port 9090
def process_data():
    received_data = request.data.decode('utf-8')
    print("Received data:", received_data)
    get_text(received_data)
    return ""

@app.route("/getAudio", methods=['POST']) 
def process_audio():
    base64_data = request.get_data(as_text=True)  # Retrieve the request data as text
    audio_bytes = base64.b64decode(base64_data)
    filename = byte_to_m4a(audio_bytes)
    transcribe = transcribe_audio(filename)
    return transcribe

def byte_to_m4a(byte_data):
    with tempfile.NamedTemporaryFile(suffix='.m4a', delete=False) as temp_file:
        temp_file.write(byte_data)
        temp_file_path = temp_file.name
    print("\n" + temp_file_path + "\n")
    return temp_file_path


def get_text(id):
    url = "http://localhost:8080/userdata/" + id
    response = requests.get(url)

    if response.status_code == 200:
        data = response.json()
        print(data)
        send_text()
    else:
        print('Error:', response.status_code)
    return ""

def send_text():
    url = "http://localhost:8080/userdata/random-content"
    data = 'Hello, World!'  # String to be sent

    headers = {'Content-Type': 'text/plain'}

    response = requests.post(url, data=data, headers=headers)

    if response.status_code == 200:
        print('Data sent successfully')
    else:
        print('Error:', response.status_code)
    return ""



if __name__ == '__main__':
    app.run(host="localhost", debug=True, port=9090)



'''
Printing text and id in python side:
Java: 8080
Python: 9090

Done:
1. post user id from java to python(9090); used to trigger python function calls
2. get text from java to python using userID (8080) http:8080/userdata/{userID}; this is unneccssary just practice doing this
3. send text from python to java (8080)

Unfinished:
4. update text from python to java (8080)
'''
