FROM selenium/hub

# Update aptitude with new repo
RUN sudo apt-get update

# Install git
RUN sudo apt-get install -y git

# Clone git repo
RUN sudo git clone https://github.com/vijayk1210/Practise.git

# Install maven
RUN sudo apt-get install -y maven

# Install jdk 8
RUN sudo apt-get install -y openjdk-8-jdk

