INSERT INTO lift_user(user_id, encoded_password, user_role) VALUES('masato', '$2a$10$fms1eItee/kPxD8FSdnk/uO9bC.CBweUrQE.CLRgZgzIGcFJVcLRu', 'ROLE_USER');
INSERT INTO lift_user(user_id, encoded_password, user_role) VALUES('masato2', '$2a$10$fms1eItee/kPxD8FSdnk/uO9bC.CBweUrQE.CLRgZgzIGcFJVcLRu', 'ROLE_USER');
INSERT INTO lift_user(user_id, encoded_password, user_role) VALUES('functionsUser', '$2a$10$fms1eItee/kPxD8FSdnk/uO9bC.CBweUrQE.CLRgZgzIGcFJVcLRu', 'ROLE_ADMIN');
INSERT INTO lift(lift_id, device_id, imsi, user_id) VALUES('100','myDevice',  '123456', 'masato');
INSERT INTO lift(lift_id, device_id, imsi, user_id) VALUES('200','myDevice2', '6266656', 'masato');
INSERT INTO lift(lift_id, device_id, imsi, user_id) VALUES('300','myDevice3', '6666656', 'masato2');
