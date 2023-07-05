import openai
import os
import wave
import io
openai.api_key = os.getenv("OPENAI_API_KEY")

#file_path = os.path.join(os.getcwd()+"\\AudioFiles", filename)

def process_audio():
    with open("sample.mp3", 'rb') as file:
        mp3_bytes = file.read()

    wav_file = io.BytesIO()
    with wave.open(wav_file, 'wb') as audio_file:
        audio_file.setnchannels(1)
        audio_file.setsampwidth(2)
        audio_file.setframerate(44100)

        audio_file.writeframes(mp3_bytes)
    print("IM HERE")
    transcribe = transcribe_audio(wav_file.getvalue())
    return transcribe

def transcribe_audio(file):
    audio_file= open(file, "rb")
    transcript = openai.Audio.transcribe(
        model = "whisper-1",
        file = audio_file,
        task= "transcribe"
    )
    return transcript.text

if __name__ == '__main__':
    print(process_audio())



