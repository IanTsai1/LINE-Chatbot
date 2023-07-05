import openai
import os
import tempfile


openai.api_key = os.getenv("OPENAI_API_KEY")

#file_path = os.path.join(os.getcwd()+"\\AudioFiles", filename)

def transcribe_audio(file):
    #file_path = os.path.join(tempfile.gettempdir() + "\\" + file)
    audio_file= open(file, "rb")
    transcript = openai.Audio.transcribe(
        model = "whisper-1",
        file = audio_file,
        task= "transcribe"
    )
    return transcript.text

if __name__ == '__main__':
    print(transcribe_audio("tmpx_4ebzjd.m4a"))





