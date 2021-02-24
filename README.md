# android-movies-challenge


## Notes

### Issue with Circle CI and Android Emulator

I found out the issue with circle ci and android emulators. Well, I'm only using the free version
of circle ci and it provides only a medium size linux vm and it doesn't support virtualization. I noticed it
when I tried the same script in a demo project in a paid circle ci account and tried the pipeline in a large and xlarge size vm
and finally it worked. Unfortunately I won't be able to get a coverage report as a artifact.