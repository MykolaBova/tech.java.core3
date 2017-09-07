for i in $(seq 1 10)
do
  curl http://localhost:9000/postings > /dev/null
done
