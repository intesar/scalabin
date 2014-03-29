vChest
======

test script
http://localhost:8080/vChest/rest/bucket/public/616bea2c-c88b-4e90-a596-65c1dc3f70c6

***START****

// Register Tenant & Admin
curl -X POST -H "Content-Type: application/json" -d '{"username":"bob2","password":"password", "tenantId":"citi2.com"}' http://localhost:8080/vChest/rest/users

// Add user to tenant
curl -X POST -H "Content-Type: application/json" -d '{"username":"li2","password":"password"}' http://localhost:8080/vChest/rest/tenant/user -u bob2@citi2.com:password

// Add bucket
curl -X POST -H "Content-Type: application/json" -d '{"name":"My Documents"}' http://localhost:8080/vChest/rest/bucket -u li2@citi2.com:password

// Add nested bucket
!!!!ALERT!!!!!
curl -X POST -H "Content-Type: application/json" -d '{"name":"Music", "parent":"44a40505-a9e8-4fec-b61c-01c1d57421ae"}' http://localhost:8080/vChest/rest/bucket -u li2@citi2.com:password

// Add nested bucket
!!!!ALERT!!!!!
curl -X POST -H "Content-Type: application/json" -d '{"name":"Pictures", "parent":"44a40505-a9e8-4fec-b61c-01c1d57421ae"}' http://localhost:8080/vChest/rest/bucket -u li2@citi2.com:password

// view home bucket
curl -X GET -H "Accept: application/json" http://localhost:8080/vChest/rest/bucket -u li2@citi2.com:password
!!!!ALERT!!!!!

// view bucket contents
curl -X GET -H "Accept: application/json" http://localhost:8080/vChest/rest/bucket/44a40505-a9e8-4fec-b61c-01c1d57421ae -u li2@citi2.com:password

// upload file
curl -i -F name=README.txt -F file=@README.txt http://localhost:8080/vChest/rest/object/ -u li2@citi2.com:password

// verify bucket contents
curl -X GET -H "Accept: application/json" http://localhost:8080/vChest/rest/bucket -u li2@citi2.com:password

!!!!ALERT!!!!!
curl -X GET http://localhost:8080/vChest/rest/object/18667673-1a6d-402f-98d4-95f62263fc7e -u li2@citi2.com:password

// search, delete, share, stats, activity

// search
curl -H "Accept: application/json" -X GET http://localhost:8080/vChest/rest/search/doc -u li2@citi2.com:password

curl -X GET -H "Accept: application/json" http://localhost:8080/vChest/rest/bucket -u li2@citi2.com:password

// delete
!!!!ALERT!!!!!
curl -H "Accept: application/json" -X DELETE http://localhost:8080/vChest/rest/bucket/cd91298b-24f6-4599-9c7e-f93a0925a791 -u li2@citi2.com:password

// verify parent
curl -X GET -H "Accept: application/json" http://localhost:8080/vChest/rest/bucket/fd23be0f-8cbe-4964-9050-cda44a51232f -u li@citi.com:password

// share to tenant
!!!!ALERT!!!!!
curl -H "Accept: application/json" -X POST http://localhost:8080/vChest/rest/bucket/public/d45fef46-13f1-4823-a990-ae390c9d1d39 -u li2@citi2.com:password

// stats
curl -H "Accept: application/json" -X GET http://localhost:8080/vChest/rest/stats/ -u li2@citi2.com:password

// activity
curl -H "Accept: application/json" -X GET http://localhost:8080/vChest/rest/activity/ -u li2@citi2.com:password
