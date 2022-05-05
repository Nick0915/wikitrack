LIB=lib
SRC=src
OUT=out
RES=res
MAIN_CLASS=WikiGame
FLAGS=-Xlint

all:
	javac ${SRC}/*.java -cp ${SRC}:${LIB}/*.jar:${RES} ${FLAGS} -d ${OUT}

run:
	java ${MAIN_CLASS} -cp ${OUT}:${LIB}/*.jar:${RES}

