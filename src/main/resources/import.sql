USE peresvitDB;
INSERT INTO  resourceType(typeName) VALUES ('TEXT'),('PHOTO'),('VIDEO'),('OTHER');
INSERT INTO  resourceGroupType(groupName) VALUES ('EVENT'),('BASE_TECHNIQUE'),('BASE_TECH_COMPLEX'),('SPECIAL_PHYSICAL_TRAININGS'),('GENERAL_PHYSICAL_TRAININGS'),('ANOTHER_SUBJECTS'),('COMPETITION');
INSERT INTO  rang(rangName) VALUES ('LEVEL_1'),('LEVEL_2'),('LEVEL_3'),('USER'), ('ADMIN');
INSERT INTO  user(firstName, lastName, email, password, avatarURL , rangId) VALUES ('test', 'test', 'admin@mail', '123456','https://cdn3.iconfinder.com/data/icons/users-6/100/654853-user-men-2-512.png', 5);
INSERT INTO  user(firstName, lastName, email, password, avatarURL , rangId) VALUES ('Василь', 'Петренко', 'user@mail', '123456','http://s3.amazonaws.com/37assets/svn/765-default-avatar.png', 4);
INSERT INTO  resourceGroup(rangId,resourceGroupTypeId) VALUES (2,2);
# INSERT INTO  user(userId,firstName,rangId) VALUES (1,'Bruce',2);
# INSERT INTO  user(userId,firstName,rangId,email,enabled,password) VALUES (1,'Lee',2,'lalala@gmail.com',b'1','123');
INSERT INTO  resource(url,title,resourceGroupId,resourceTypeId) VALUES ('some_url','some_photo1',1,2);
INSERT INTO  resource(url,title,resourceGroupId,resourceTypeId) VALUES ('some_url','some_photo2',1,2);
INSERT INTO  resource(url,title,resourceGroupId,resourceTypeId) VALUES ('some_url','some_doc1',1,1);
INSERT INTO  resource(url,title,resourceGroupId,resourceTypeId) VALUES ('some_url','some_doc2',1,1);
INSERT INTO  resource(url,title,resourceGroupId,resourceTypeId) VALUES ('some_url','some_video',1,3);
# INSERT INTO  resource(title) VALUES ('avatar');