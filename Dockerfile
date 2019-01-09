FROM vertx/vertx3

#ENV VARIABLES
ENV VERTICLE_NAME io.vertx.bunch.of.passwords.KeyPassVerticle
ENV VERTICLE_FILE target/bunch-of-passwords-1.0.0-SNAPSHOT.jar

ENV VERTICLE_HOME /usr/vertivles

#EXPOSE PORTE
EXPOSE 8080
COPY $VERTICLE_FILE $VERTICLE_HOME/

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]
