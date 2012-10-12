#!/bin/bash
#
# Test restful cest
# (testy, které jsou označené hvězdičkou mají skončit s chybou)
# 
# Akce    Metoda  URL
# index   GET     /
# create  POST    /
# new     GET     /new
# edit    GET     /:id/edit
# show    GET     /:id
# update  PUT     /:id
# delete  DELETE  /:id
# 


host="localhost"
port=8084

function test(){
  action=$1
  method=$2
  url=$3
  t=$4
  [[ ! $t ]] && t=0
  
  [[ $t -eq 1 ]] && echo -ne '\e[00;33m*\e[00m'
  echo -n "$action($method $url) ..."
  nc $host $port <<EOF | grep "action: $action" >/dev/null
$method /RestTest/items$url HTTP/1.1
Host: localhost


EOF
  if [ $? -eq $t ]; then
    echo -e '\e[00;32mOK\e[00m'
  else
    echo -e '\e[00;31mFAIL\e[00m'
  fi
}

test "index" "GET" "/"
test "index" "GET" ""
test "index" "POST" "/" 1
test "index" "PUT" "/" 1
test "index" "DELETE" "/" 1
test "new" "GET" "/new"
test "new" "POST" "/new" 1
test "new" "PUT" "/new" 1
test "create" "POST" "/"
test "create" "POST" "/1" 1
test "show" "GET" "/asd" 1
test "show" "GET" "/$RANDOM"
test "edit" "GET" "/$RANDOM/edit"
test "edit" "GET" "/edit" 1
test "edit" "POST" "/$RANDOM/edit" 1
test "update" "PUT" "/$RANDOM"
test "update" "POST" "/$RANDOM" 1
test "update" "GET" "/$RANDOM" 1
test "delete" "DELETE" "/$RANDOM"


