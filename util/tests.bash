# create customer (artist) and returns id
curl --header "Content-Type: application/json"  --request POST  --data '{ "name": "O Rappa", "country": "BR",  "musicStyle": "Rock"}' http://localhost:8080/v1/

# create music
curl --header "Content-Type: application/json" --request POST --data '{ "name": "Musica 01"}' http://localhost:8082/v1/customers/${customer-id}/musics

# uplaod music 
curl -X POST  -H "Content-Type: multipart/form-data"  -F "file=@musica01.mp3"  http://localhost:8085/v1/customers/${customer-id}/musics/${music-id}
