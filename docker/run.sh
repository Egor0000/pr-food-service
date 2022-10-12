#!/usr/bin/env bash

export FOOD_ORDERING_VERSION=food.aggregator:latest
export PORT=8002
export APP_NAME=food-ordering-service
export ADDRESS=localhost

function noArgumentSupplied() {

    echo ""
    echo "========================================================================="
    echo ""

    exit 1
}

args=("$@")

echo "========================================================================="
echo ""
echo "  Food Ordering Service Docker"
echo ""
echo "  Number of arguments: $#"

if [[ $# -eq 0 ]] ; then
    echo '  No arguments supplied'
    noArgumentSupplied
    exit 1
fi

if [[ -z ${args[0]} ]] ; then
    echo '  no example instance name supplied'
    noArgumentSupplied
    exit 1
fi

export FOOD_ORDERING_INSTANCE_NAME=${args[0]}

echo "  Food ordering service instance name: ${FOOD_ORDERING_INSTANCE_NAME}"
echo ""
echo "========================================================================="
echo ""

docker run -it --name ${FOOD_ORDERING_INSTANCE_NAME} --net host --log-driver none \
-e ADDRESS=${ADDRESS} \
-e PORT=${PORT} \
-e APP_NAME=${APP_NAME} \
-e JAVA_OPTS="${JAVA_OPTS}" \
--rm ${FOOD_ORDERING_VERSION}