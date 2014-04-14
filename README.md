vChest
======

====================================================================

    # drop all indexes
    http://localhost:8983/solr/update?stream.body=%3Cdelete%3E%3Cquery%3E*:*%3C/query%3E%3C/delete%3E&commit=true
    
    #solr schema changes. Schema.xml content.
    
    
    <field name="users_username" type="text_general" indexed="true" stored="true"/>
    <field name="users_password" type="text_general" indexed="true" stored="true"/>
    <field name="users_enabled" type="boolean" indexed="true" stored="true"/>
    <field name="users_tenantId" type="text_general" indexed="true" stored="true"/>
    <field name="users_groupId" type="text_general" indexed="true" stored="true"/>


    <field name="username" type="text_general" indexed="true" stored="true"/>
    <field name="password" type="text_general" indexed="true" stored="true"/>
    <field name="enabled" type="boolean" indexed="true" stored="true"/>
    
    <field name="tenantId" type="text_general" indexed="true" stored="true"/>
    <field name="groupId" type="text_general" indexed="true" stored="true"/>

    <field name="userId" type="text_general" indexed="true" stored="true"/>
    <field name="authority" type="text_general" indexed="true" stored="true"/>
	<!-- you might see this already defined.
	<field name="name" type="text_general" indexed="true" stored="true"/>
	-->
	<field name="location" type="text_general" indexed="true" stored="true"/>
	<field name="size" type="long" indexed="true" stored="true"/>
	<field name="modified" type="date" indexed="true" stored="true"/>
	<field name="chunkCount" type="long" indexed="true" stored="true"/>
	<field name="itemCount" type="long" indexed="true" stored="true"/>
	<field name="kind" type="text_general" indexed="true" stored="true"/>
	<field name="shared" type="text_general" indexed="true" stored="true"/>
	
	<field name="actionDate" type="date" indexed="true" stored="true"/>
	<field name="actionType" type="text_general" indexed="true" stored="true"/>
	
	<field name="activity_username" type="text_general" indexed="true" stored="true"/>
	<field name="activity_actionDate" type="date" indexed="true" stored="true"/>
	<field name="activity_actionType" type="text_general" indexed="true" stored="true"/>
	<field name="activity_objId" type="text_general" indexed="true" stored="true"/>
	<field name="activity_objName" type="text_general" indexed="true" stored="true"/>
	<field name="activity_size" type="long" indexed="true" stored="true"/>
	<field name="activity_groupId" type="text_general" indexed="true" stored="true"/>
	<field name="activity_tenantId" type="text_general" indexed="true" stored="true"/>
	
	<field name="stats_username" type="text_general" indexed="true" stored="true"/>
	<field name="stats_groupId" type="text_general" indexed="true" stored="true"/>
	<field name="stats_tenantId" type="text_general" indexed="true" stored="true"/>
	
	<field name="objId" type="text_general" indexed="true" stored="true"/>
	<field name="objName" type="text_general" indexed="true" stored="true"/>
	
    <field name="year" type="long" indexed="true" stored="true"/>
    <field name="month" type="long" indexed="true" stored="true"/>
    <field name="storage" type="long" indexed="true" stored="true"/>
    <field name="uploadedBytes" type="long" indexed="true" stored="true"/>
    <field name="downloadedBytes" type="long" indexed="true" stored="true"/>

    <field name="home" type="boolean" indexed="true" stored="true"/>    
    <field name="parent" type="text_general" indexed="true" stored="true"/>
    <field name="owner" type="text_general" indexed="true" stored="true"/>
    

    
=============================================================================

// run the below commends on cql cli.

	 
     
     DROP COLUMNFAMILY users;
     DROP COLUMNFAMILY authority;
     DROP COLUMNFAMILY obj;
     DROP COLUMNFAMILY blob;
     DROP COLUMNFAMILY activity;
     DROP COLUMNFAMILY stats;
     
	 DROP KEYSPACE vchest;
     
     create keyspace vchest with replication = {'class':'SimpleStrategy', 'replication_factor':1} ;
     use vchest ;
    
     CREATE COLUMNFAMILY users ( id varchar PRIMARY KEY, username varchar, password varchar, enabled boolean, groupId varchar, tenantId varchar);
	 ALTER COLUMNFAMILY users ADD name varchar;
	 
	 CREATE COLUMNFAMILY authority( id varchar PRIMARY KEY, userId varchar, authority varchar );
	 
	 CREATE COLUMNFAMILY obj ( id varchar PRIMARY KEY, name varchar, kind  varchar, location varchar, size  bigint, 
	 parent varchar, modified timestamp, chunkCount int, owner varchar, tenantId varchar, groupId varchar, shared varchar);
	 
	 ALTER COLUMNFAMILY obj ADD itemCount int;
	 ALTER COLUMNFAMILY obj ADD home boolean;
	 
	 CREATE COLUMNFAMILY binary_content( id varchar PRIMARY KEY, objId varchar, content blob);
	 
     CREATE COLUMNFAMILY activity( id varchar PRIMARY KEY, username varchar, actionDate timestamp, actionType varchar, objId varchar, objName varchar, 
     	size int, groupId varchar, tenantId varchar);
     
     CREATE COLUMNFAMILY stats(id varchar PRIMARY KEY, username varchar, year int, month int, storage int, uploadedBytes int,
     downloadedBytes int, groupid varchar, tenantId varchar);
     
     
 =====================================================================================    

# API test script

***START****

// Register Tenant & Admin
curl -X POST -H "Content-Type: application/json" -d '{"username":"imran","password":"password", "tenantId":"imran.com"}' http://localhost:8080/vChest/rest/users

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
curl -X GET -H "Accept: application/json" http://localhost:8080/vChest/rest/bucket -u kulsum@bia.com:password
!!!!ALERT!!!!!

// view bucket contents
curl -X GET -H "Accept: application/json" http://localhost:8080/vChest/rest/bucket/44a40505-a9e8-4fec-b61c-01c1d57421ae -u li2@citi2.com:password

// upload file
curl -i -F name=README.txt -F file=@README.txt http://localhost:8080/vChest/rest/object/ -u li2@citi2.com:password

// verify bucket contents
curl -X GET -H "Accept: application/json" http://localhost:8080/vChest/rest/bucket -u amjad@amj.com:password

// download
!!!!ALERT!!!!!
curl -X GET http://localhost:8080/vChest/rest/object/142dcbcd-acb6-4b30-a808-58b6826ae271 -u amjad@amj.com:password

// search, delete, share, stats, activity

// search
curl -H "Accept: application/json" -X GET http://localhost:8080/vChest/rest/search/doc -u li2@citi2.com:password

curl -X GET -H "Accept: application/json" http://localhost:8080/vChest/rest/bucket -u li2@citi2.com:password

// delete
!!!!ALERT !!!!!
curl -H "Accept: application/json" -X DELETE http://localhost:8080/vChest/rest/bucket/cd91298b-24f6-4599-9c7e-f93a0925a791 -u li2@citi2.com:password

// verify parent
curl -X GET -H "Accept: application/json" http://localhost:8080/vChest/rest/bucket/fd23be0f-8cbe-4964-9050-cda44a51232f -u li@citi.com:password

// share to tenant
!!!!ALERT!!!!!
curl -H "Accept: application/json" -X POST http://localhost:8080/vChest/rest/bucket/public/d45fef46-13f1-4823-a990-ae390c9d1d39 -u li2@citi2.com:password

// stats
curl -H "Accept: application/json" -X GET http://localhost:8080/vChest/rest/stats/ -u amjad@amj.com:password

// activity
curl -H "Accept: application/json" -X GET http://localhost:8080/vChest/rest/activity/ -u li2@citi2.com:password


Performance nos
1 MB 3000 uploads - 18 seconds
1 MB 4000 uploads - 15 seconds
1 MB 10000 uploads - 18 seconds

maxWarmingSearchers=50jiuhgy 
*** 1 MB 300 concurrent uploads - 12 seconds **

// misc
https://www.serverpronto.com/accounts/knowledgebase.php?action=displayarticle&id=2
http://stackoverflow.com/questions/18724334/cant-connect-to-cassandra-nohostavailableexception
https://www.centos.org/docs/5/html/5.1/Deployment_Guide/s1-server-ports.html
http://zee.linxsol.com/system-administration/qdevice-eth0-does-not-seem-to-be-presentq-after-moving-or-cloning-a-rhelcentos-64-virtual-machine-in-vsphere.html
http://stackoverflow.com/questions/19034542/how-to-open-port-in-centos



