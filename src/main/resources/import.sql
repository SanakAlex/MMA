USE peresvitDB;
INSERT INTO  resourceType(typeName) VALUES ('TEXT'),('PHOTO'),('VIDEO'),('OTHER');
INSERT INTO  resourceGroupType(groupName) VALUES ('EVENT'),('BASE_TECHNIQUE'),('BASE_TECH_COMPLEX'),('SPECIAL_PHYSICAL_TRAININGS'),('GENERAL_PHYSICAL_TRAININGS'),('ANOTHER_SUBJECTS'),('COMPETITION');
INSERT INTO  rang(rangName) VALUES ('LEVEL_1'),('LEVEL_2'),('LEVEL_3'),('ROLE_ADMIN');
INSERT INTO  user(firstName, lastName, email, password, rangId) VALUES ('test', 'test', 'test@test', '123', 4);
INSERT INTO  resourceGroup(rangId,resourceGroupTypeId) VALUES (2,2);
INSERT INTO  user(userId,firstName,rangId) VALUES (1,'test',2);
INSERT INTO  resource(title,userId,resourceTypeId) VALUES ('avatar',1,1);
INSERT INTO  resource(title,resourceGroupId,resourceTypeId) VALUES ('avatar1',1,1);
INSERT INTO  resource(title,resourceGroupId,resourceTypeId) VALUES ('avatar2',1,1);
# INSERT INTO  resource(title) VALUES ('avatar');