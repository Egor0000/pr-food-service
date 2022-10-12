#!/usr/bin/env bash
# start time
start=$(date +%s.%N)

DATE=`date +%Y%m%d`

# To enable BuildKit builds
DOCKER_BUILDKIT=1

# delete target
rm -r -f target/

docker build --network=host -t food.aggregator:latest -t food.aggregator:$DATE -f Dockerfile .

# docker manual GC
docker rmi -f $(docker images -f "dangling=true" -q)

# docker save and gzip
docker save food.aggregator

# end time
end=$(date +%s.%N)
# run time
runtime=$(python3 -c "print(${end} - ${start})")
echo "Runtime was $runtime seconds"
