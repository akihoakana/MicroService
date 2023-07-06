CREATE TABLE IF NOT EXISTS roles (
    id SERIAL  NOT NULL ,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS users (
    id SERIAL  NOT NULL ,
    email VARCHAR(100) NOT NULL,
	username VARCHAR(100) ,
    password VARCHAR(100) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    firstname VARCHAR(100) ,
	issend boolean default false,
    lastname VARCHAR(100) ,
    phone VARCHAR(15) ,
    country VARCHAR(100) ,
    avatar VARCHAR(100),
    PRIMARY KEY (id)
);

INSERT INTO roles( name, description ) VALUES ('ROLE_ADMIN', 'Quản trị viên');
INSERT INTO roles( name, description ) VALUES ('ROLE_MANAGER', 'Quản lý');
INSERT INTO roles( name, description ) VALUES ('ROLE_DEVELOPER', 'Lập trình viên');
INSERT INTO roles( name, description ) VALUES ('ROLE_MEMBER', 'Thành viên');
INSERT INTO roles( name, description ) VALUES ('ROLE_SUPPORTER', 'Nhân viên hỗ trợ');

INSERT INTO users(issend,email,password,fullname,firstname,lastname,username,phone,country,avatar  ) 
VALUES ('false','akihoakana@gmail.com','456789','Nguyễn Văn D','D','Nguyễn','dnv','','','');

INSERT INTO users(issend,email,password,fullname,phone,country ) 
VALUES ('false','nguyenvana@gmail.com','123456','Nguyễn Văn A','011111111','Nguyễn');

INSERT INTO users(issend,email,password,fullname,firstname,lastname,username,phone,country,avatar  ) 
VALUES ('false','nguyenvanb@gmail.com','123456','Nguyễn Văn A','A','Nguyễn','anv','','','');

INSERT INTO users(issend,email,password,fullname,firstname,lastname,username,phone,country,avatar  ) 
VALUES ('false','nguyenvanc@gmail.com','456','Nguyễn Văn B','B','Nguyễn','bnv','','','');

INSERT INTO users(issend,email,password,fullname,firstname,lastname,username,phone,country,avatar  ) 
VALUES ('false','nguyenvand@gmail.com','789','Nguyễn Văn C','C','Nguyễn','cnv','','','');

INSERT INTO users(issend,email,password,fullname,firstname,lastname,username,phone,country,avatar  ) 
VALUES ('false','nguyenvane@gmail.com','456789','Nguyễn Văn D','D','Nguyễn','dnv','','','');


ALTER TABLE users 
ADD COLUMN role_id int ;

ALTER TABLE users ADD CONSTRAINT fk_orders_customers
FOREIGN KEY (role_id)
REFERENCES roles (id);

select  users.*
from users;

select  users.id,users.email,users.username,users.fullname from users;

select roles.*
from roles;

select  users.*,roles.*
from users
JOIN roles
ON roles.id = users.id;
	
update users 
set role_id =4
where users.id=3;

delete from users where users.id >1;

delete from roles where roles.id >5;