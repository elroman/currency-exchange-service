#!/usr/bin/env bash
invoke()
{
	echo "# `date`: $@"
	$@
}

invoke docker build --tag='elroman/ces' `dirname $0`/docker
[ $? != 0 ] && exit 1

invoke docker rm -f ces

invoke docker run -d --name ces -p 8080:8080 elroman/ces
[ $? != 0 ] && exit 1

invoke sleep 10