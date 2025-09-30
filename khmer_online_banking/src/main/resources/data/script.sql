GRANT ALL PRIVILEGES ON SCHEMA public TO online_banking_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO online_banking_user;



INSERT INTO "public"."role" ("id", "status", "version", "created_at", "updated_at", "name", "role")
VALUES (1, 't', 0, null, null, 'Admin', 'ADMIN');
INSERT INTO "public"."role" ("id", "status", "version", "created_at", "updated_at", "name", "role")
VALUES (2, 't', 0, null, null, 'User', 'USER');
