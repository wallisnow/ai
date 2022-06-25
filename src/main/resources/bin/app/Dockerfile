FROM anibali/pytorch:1.8.1-cuda11.1-ubuntu20.04

# Set up time zone.
ENV TZ=UTC
RUN sudo ln -snf /usr/share/zoneinfo/$TZ /etc/localtime

ENV LC_ALL=C.UTF-8 \
    LANG=C.UTF-8

# Install system libraries required by OpenCV.
#RUN sudo apt-get update \
# && sudo apt-get install -y libgl1-mesa-glx libgtk2.0-0 libsm6 libxext6 \
# && sudo rm -rf /var/lib/apt/lists/*

# Install OpenCV from PyPI.
# RUN pip install opencv-python==4.5.1.48

RUN mkdir -p ./script
COPY app/script/ /app/script/

RUN pip install -r ./script/requirements.txt

CMD [ "python", "./script/main.py" ]


