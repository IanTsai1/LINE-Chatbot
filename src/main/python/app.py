import openai
audio_file= open("../AudioFiles/elephant.mp4", "rb")


transcript = openai.Audio.transcribe(
    model = "whisper-1",
    file = audio_file,
    task= "transcribe"
    )
print(transcript.text)
