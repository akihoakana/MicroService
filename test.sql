DROP TABLE mess_reply; 
DROP TABLE mess; 
DROP TABLE reaction_status; 
DROP TABLE reaction; 
DROP TABLE status; 
DROP TABLE users;  


CREATE TABLE IF NOT EXISTS reaction (
    id SERIAL  NOT NULL ,
    detail VARCHAR(10) NOT NULL ,
    PRIMARY KEY (id)
);

INSERT INTO reaction(detail) 
VALUES ('LIKE');
INSERT INTO reaction(detail) 
VALUES ('LOVE');
INSERT INTO reaction(detail) 
VALUES ('HAHA');
INSERT INTO reaction(detail) 
VALUES ('ANGRY');

CREATE TABLE IF NOT EXISTS users (
    id SERIAL  NOT NULL ,
    email VARCHAR(100) NOT NULL UNIQUE,
	username VARCHAR(100) UNIQUE,
    password VARCHAR(100) NOT NULL,
	verify_active boolean DEFAULT false,
    firstname VARCHAR(100) ,
    lastname VARCHAR(100) ,
    phone VARCHAR(15) UNIQUE,
    avatar VARCHAR(100),
    PRIMARY KEY (id)
);
INSERT INTO users(email,username,password) 
VALUES ('akihoakana@gmail.com','akihoakana@gmail.com','456789');
INSERT INTO users(email,username,password) 
VALUES ('nguyenvana@gmail.com','nguyenvana@gmail.com','nguyenvana');
INSERT INTO users(email,username,password) 
VALUES ('nguyenvanb@gmail.com','nguyenvanb@gmail.com','nguyenvanb');
INSERT INTO users(email,username,password) 
VALUES ('nguyenvanc@gmail.com','nguyenvanc@gmail.com','nguyenvanc');
INSERT INTO users(email,username,password) 
VALUES ('nguyenvand@gmail.com','nguyenvand@gmail.com','nguyenvand');
INSERT INTO users(email,username,password) 
VALUES ('nguyenvane@gmail.com','nguyenvane@gmail.com','nguyenvane');


CREATE TABLE IF NOT EXISTS mess (
    id SERIAL  NOT NULL ,
    mess_detail VARCHAR(1550) NOT NULL ,
	time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    fromuser_id int ,
	touser_id int ,
    PRIMARY KEY (id),
	CONSTRAINT fromuser_id FOREIGN KEY(fromuser_id) REFERENCES users(id),
	CONSTRAINT touser_id FOREIGN KEY(touser_id) REFERENCES users(id)
);


INSERT INTO mess( mess_detail, fromuser_id,touser_id ) 
VALUES ('nguyennvan1', 1,2);
INSERT INTO mess( mess_detail, fromuser_id,touser_id ) 
VALUES ('nguyennvan11', 1,2);
INSERT INTO mess( mess_detail, fromuser_id,touser_id ) 
VALUES ('nguyennvan2', 2,1);
INSERT INTO mess( mess_detail, fromuser_id,touser_id ) 
VALUES ('nguyennvan22', 2,1);

INSERT INTO mess( mess_detail, fromuser_id,touser_id ) 
VALUES ('ngzzzzan2', 2,3);
INSERT INTO mess( mess_detail, fromuser_id,touser_id ) 
VALUES ('nguxxxxxvan22', 2,4);

INSERT INTO mess( mess_detail, fromuser_id,touser_id ) 
VALUES ('aaanvan2', 1,3);
INSERT INTO mess( mess_detail, fromuser_id,touser_id ) 
VALUES ('ngvvvvvnvan22', 1,4);
-- INSERT INTO users_roles(users_id) 
-- VALUES (1);

-- CREATE TABLE IF NOT EXISTS mess_reply (
--     id SERIAL  NOT NULL ,
-- 	reply text,
-- 	time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
--     mess_id int ,
-- 	user_id int ,
--     PRIMARY KEY (id),
-- 	CONSTRAINT mess_id FOREIGN KEY(mess_id) REFERENCES mess(id),
-- 	CONSTRAINT user_id FOREIGN KEY(user_id) REFERENCES users(id)
-- );

CREATE TABLE IF NOT EXISTS status (
    id SERIAL  NOT NULL ,
    detail VARCHAR(1550) NOT NULL ,
    user_id int ,
	created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP ,
    PRIMARY KEY (id),
	CONSTRAINT user_id FOREIGN KEY(user_id) REFERENCES users(id)
);

INSERT INTO status(detail,user_id) 
VALUES ('aaaaaaaaaa',1);

INSERT INTO status(detail,user_id) 
VALUES ('bbbbbbbbbbb',2);

INSERT INTO status(detail,user_id) 
VALUES ('ccccccccccc',3);

INSERT INTO status(detail,user_id) 
VALUES ('bbbbbbbbbbbbbbb',1);

CREATE TABLE IF NOT EXISTS reaction_status (
	id SERIAL  NOT NULL ,
    reaction_id int NOT NULL ,
    status_id int NOT NULL ,
	users_id int NOT NULL ,
	created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP ,
    PRIMARY KEY (id),
	CONSTRAINT reaction_id FOREIGN KEY(reaction_id) REFERENCES reaction(id),
	CONSTRAINT users_id FOREIGN KEY(users_id) REFERENCES users(id),
	CONSTRAINT status_id FOREIGN KEY(status_id) REFERENCES status(id)
);

INSERT INTO reaction_status(reaction_id,status_id,users_id) 
VALUES (1,1,1);

INSERT INTO reaction_status(reaction_id,status_id,users_id) 
VALUES (2,1,2);

INSERT INTO reaction_status(reaction_id,status_id,users_id) 
VALUES (3,2,1);

INSERT INTO reaction_status(reaction_id,status_id,users_id) 
VALUES (4,2,3);

-- UPDATE reaction_status 
-- SET reaction_id = 4 , created= CURRENT_TIMESTAMP 
-- WHERE users_id= 3 and status_id= 2;

select  users.*
from users ;

select  *
from mess;

select  *
from reaction_status;
-- select  *
-- from mess where fromuser_id=2 and touser_id=1;

-- select  *
-- from mess where touser_id=2;

-- SELECT
--     users.id,
--     mess.*
-- FROM
--     users
-- JOIN mess
--     ON users.id = mess.fromuser_id; 
-- 	and users.id = mess.touser_id;
-- select  users.id,users.email,users.username from users;


insert into users (email, password) values ('mpurry0@techcrunch.com', 'KlC7DYPBEN');
insert into users (email, password) values ('mlongstreet1@sciencedirect.com', 'eyWkcGBG');
insert into users (email, password) values ('alegassick2@slashdot.org', 'qIbSiDQezF');
insert into users (email, password) values ('kgradley3@prnewswire.com', 'womgLx');
insert into users (email, password) values ('kschukraft4@miitbeian.gov.cn', '7yO8pt1SNEy');
insert into users (email, password) values ('emacdermot5@github.io', 'w5VQQz');
insert into users (email, password) values ('tshire6@ovh.net', 'xM4XMh07E');
insert into users (email, password) values ('hstourton7@myspace.com', 'wLAmW4v2');
insert into users (email, password) values ('abeckley8@thetimes.co.uk', 'jPB2I8');
insert into users (email, password) values ('jcuthbertson9@illinois.edu', 'TVdai1XZNht');
insert into users (email, password) values ('adumbrilla@fema.gov', 'Q1toF1V1w1');
insert into users (email, password) values ('nsnelsonb@irs.gov', 'OqldbMIe');
insert into users (email, password) values ('ljilkesc@blogs.com', 'Y6AHmOl');
insert into users (email, password) values ('focorrend@hatena.ne.jp', 'GyjiHvI');
insert into users (email, password) values ('bzmitrichenkoe@ameblo.jp', 'VoTn7Cxsm4hh');
insert into users (email, password) values ('dkinsellf@umn.edu', 'xhH6Pp7phX');
insert into users (email, password) values ('rvanrossg@shop-pro.jp', '6TRjYe');
insert into users (email, password) values ('jjedrzaszkiewiczh@artisteer.com', 'WLnV7KpdOS1');
insert into users (email, password) values ('cstendalli@rediff.com', 'EeaKXyEC5eCG');
insert into users (email, password) values ('yousleyj@clickbank.net', 'cC7FFn');
insert into users (email, password) values ('smacklamk@hao123.com', 'cUQaeSVN');
insert into users (email, password) values ('dsueterl@bravesites.com', 'jcbtSZ');
insert into users (email, password) values ('cmycockm@domainmarket.com', 'fEjzIv2ZYOPy');
insert into users (email, password) values ('epaulingn@bluehost.com', 'YlPY7Wt');
insert into users (email, password) values ('lleehaneo@berkeley.edu', '2hwWwvJk');
insert into users (email, password) values ('fvedstrap@elegantthemes.com', 'RLmnBMU');
insert into users (email, password) values ('aratkeq@php.net', '3LEZc1u7');
insert into users (email, password) values ('bgooldingr@blinklist.com', 'lDiflU8NqtL');
insert into users (email, password) values ('oallmens@accuweather.com', 't5MQzvMr9j');
insert into users (email, password) values ('glebosset@macromedia.com', 'n37ppj7Lu');
insert into users (email, password) values ('bfrostdykeu@gov.uk', 'e3IQ1ebD');
insert into users (email, password) values ('bsicilyv@uol.com.br', 'AKH2jWY38');
insert into users (email, password) values ('phatliffw@homestead.com', 'WQDWKEXkZrch');
insert into users (email, password) values ('gbraniganx@cbslocal.com', 'IzzdybtNKjAC');
insert into users (email, password) values ('helsey@ustream.tv', 'XKOFeWel8');
insert into users (email, password) values ('sbedomez@unicef.org', 'cYuMeQ8u');
insert into users (email, password) values ('lcoard10@apple.com', 'BwFg5G8O');
insert into users (email, password) values ('vmcmackin11@netlog.com', 'dPl0dGPQUtH');
insert into users (email, password) values ('bwhiff12@bbb.org', 'SxjlEVO4lRew');
insert into users (email, password) values ('wstockow13@simplemachines.org', 'Ma6bGObP');
insert into users (email, password) values ('oshilliday14@gnu.org', '4mi668');
insert into users (email, password) values ('jveldstra15@nba.com', 'dYPFEp');
insert into users (email, password) values ('kbreitler16@tinypic.com', 'KgHMZVan0n');
insert into users (email, password) values ('dsimenet17@angelfire.com', '19q8rixM');
insert into users (email, password) values ('fkeuneke18@arizona.edu', 'VTrfvRwzvB');
insert into users (email, password) values ('pgotthardsf19@cbc.ca', 'VYYxF0Hu');
insert into users (email, password) values ('kmillsom1a@arizona.edu', 'kjwu8XLo');
insert into users (email, password) values ('jcaddan1b@cam.ac.uk', 'g60yuEW5');
insert into users (email, password) values ('hvanetti1c@topsy.com', 'apPNyEqn');
insert into users (email, password) values ('jblenkharn1d@vimeo.com', 'lPmI1m');
insert into users (email, password) values ('fcrosen1e@hhs.gov', '74ylp2');
insert into users (email, password) values ('kdunbobbin1f@youku.com', 'j0hXuL8Wh');
insert into users (email, password) values ('rpawels1g@skyrock.com', 'B3poA46ihA');
insert into users (email, password) values ('vcarrigan1h@artisteer.com', 'moYq7ujy');
insert into users (email, password) values ('cgon1i@printfriendly.com', 'cyh2FPV');
insert into users (email, password) values ('dsnoddon1j@japanpost.jp', 'b2tmwnAc');
insert into users (email, password) values ('ajanuszewicz1k@tiny.cc', '7KKFnxpGSfz');
insert into users (email, password) values ('hkibble1l@jalbum.net', 'mxsyX5w6EEpZ');
insert into users (email, password) values ('egerrish1m@hhs.gov', 'SUVIUB');
insert into users (email, password) values ('mwinsome1n@newyorker.com', 'GOXWQvFY5q0I');
insert into users (email, password) values ('cmaclure1o@foxnews.com', '7iCylaClsEd6');
insert into users (email, password) values ('gwray1p@tinyurl.com', 'tYz6q5CP4o');
insert into users (email, password) values ('nwayte1q@accuweather.com', '9ZY4nuFnh6');
insert into users (email, password) values ('gdimitrie1r@dailymail.co.uk', 'ARa8Snpb');
insert into users (email, password) values ('shewins1s@spiegel.de', 'icQVrRSYG');
insert into users (email, password) values ('hsalerno1t@ed.gov', '2PADqJYZXw');
insert into users (email, password) values ('lorae1u@house.gov', 'wcvsYfi');
insert into users (email, password) values ('aboyne1v@goo.ne.jp', 'aewIMZgt2');
insert into users (email, password) values ('rvalentinuzzi1w@canalblog.com', 'Bt4BHAd');
insert into users (email, password) values ('rimloch1x@google.com.br', 'tJ7qZd');
insert into users (email, password) values ('lready1y@phoca.cz', 'CRLfjA');
insert into users (email, password) values ('ltracey1z@hibu.com', 'eVoG8LlSF');
insert into users (email, password) values ('mgemson20@theatlantic.com', '4KiB2us');
insert into users (email, password) values ('crospars21@seesaa.net', 'eOmuTY3a');
insert into users (email, password) values ('emarrington22@google.com.br', 'TD5D3QFO');
insert into users (email, password) values ('cshiel23@alibaba.com', 'TyJfM5KltM');
insert into users (email, password) values ('hseeks24@symantec.com', 'eUNtWkQkKt');
insert into users (email, password) values ('nmourgue25@jiathis.com', 'P2VAFsulHq');
insert into users (email, password) values ('smartlew26@ibm.com', 'oa4tqJ22e');
insert into users (email, password) values ('ryurocjkin27@ted.com', 'aNRWJTQVyY7n');
insert into users (email, password) values ('ecourt28@cargocollective.com', 'muEgjYw');
insert into users (email, password) values ('ehousam29@earthlink.net', 'vdeRcyM');
insert into users (email, password) values ('sstonehouse2a@upenn.edu', 'dNi3MKY');
insert into users (email, password) values ('tsellens2b@chronoengine.com', 'gCXxnP');
insert into users (email, password) values ('hfronczak2c@earthlink.net', 'WfhT7tL');
insert into users (email, password) values ('gholstein2d@imgur.com', 'iRaG0SpwGfYh');
insert into users (email, password) values ('dkopp2e@sohu.com', '66ZIyz2jxU');
insert into users (email, password) values ('ocoleridge2f@ning.com', 'qwqINR5');
insert into users (email, password) values ('severly2g@cbc.ca', 'J0Y3YQCa5my');
insert into users (email, password) values ('adupree2h@facebook.com', 'm2WPeSVUToIa');
insert into users (email, password) values ('ltownrow2i@mayoclinic.com', 'LKjyt9');
insert into users (email, password) values ('rsantorini2j@howstuffworks.com', 'Aad6blc');
insert into users (email, password) values ('shopfer2k@webeden.co.uk', 'DM5r9I');
insert into users (email, password) values ('gloveridge2l@miitbeian.gov.cn', 'BbQz9d4xwb4K');
insert into users (email, password) values ('lgrowcott2m@bloomberg.com', 'Z8gdUe7Wc6');
insert into users (email, password) values ('vcullington2n@ebay.com', 'zlSe89ptuKcR');
insert into users (email, password) values ('sreyna2o@mlb.com', 'TTB9mCFqDZZX');
insert into users (email, password) values ('bscrancher2p@ehow.com', 'jt3vkJ9o');
insert into users (email, password) values ('whodcroft2q@joomla.org', '25bvxzDmc25');
insert into users (email, password) values ('dbrowne2r@networksolutions.com', 'Ow9DrAM');
