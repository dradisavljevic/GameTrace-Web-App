CREATE OR REPLACE TRIGGER GTUSER_INSTRIG
BEFORE INSERT ON GT_USER
FOR EACH ROW
BEGIN
    IF :NEW.UDATE IS NULL THEN
        :NEW.UDATE := SYSDATE;
    END IF;
END GTUSER_INSTRIG;
/
CREATE OR REPLACE TRIGGER GAMEUSER_INSTRIG
BEFORE INSERT ON GAME_USER
FOR EACH ROW
BEGIN
    IF :NEW.UBIO IS NULL THEN
        :NEW.UBIO := 'N\A';
    END IF;
END GAMEUSER_INSTRIG;
/
CREATE OR REPLACE TRIGGER PLAYERNUM
BEFORE INSERT OR UPDATE ON PLAYS
FOR EACH ROW
DECLARE
v_number INTEGER;
v_players INTEGER;
BEGIN
    SELECT COUNT(*) INTO v_number FROM PLAYS WHERE GAME_USER_UNAME=:NEW.GAME_USER_UNAME AND GAME_GAMEID=:NEW.GAME_GAMEID;
    IF (v_number = 0) THEN
        SELECT GAMEPN INTO v_players FROM GAME WHERE GAMEID=:NEW.GAME_GAMEID;
        v_players:=v_players+1;
        UPDATE GAME SET GAMEPN=v_players WHERE GAMEID=:NEW.GAME_GAMEID;
    END IF;
END;
/
CREATE OR REPLACE TRIGGER DELETE_GAMEUSER
BEFORE DELETE ON GT_USER
FOR EACH ROW
DECLARE
v_user GT_USER.UNAME%TYPE;
BEGIN
    v_user:=:OLD.UNAME;
    DELETE FROM REQUEST WHERE REQSUB=v_user;
    DELETE FROM REVIEW WHERE USER_UNAME=v_user;
    DELETE FROM RANKS WHERE GAME_USER_UNAME=v_user;
    DELETE FROM EARNED WHERE GAME_USER_UNAME=v_user;
    DELETE FROM ITEM WHERE GAME_USER_UNAME=v_user;
    DELETE FROM RECEIVED WHERE USER_UNAME=v_user;
    DELETE FROM GUIDE WHERE GAME_USER_UNAME=v_user;
    DELETE FROM GAME_GROUP WHERE GRGM=v_user;
    DELETE FROM FRIEND_REQUEST WHERE SENDER=v_user;
    DELETE FROM FRIEND_REQUEST WHERE RECEIVER=v_user;
    DELETE FROM PARTICIPATING_IN WHERE GAME_USER_UNAME=v_user;
    DELETE FROM PLAYS WHERE GAME_USER_UNAME=v_user;
    DELETE FROM TICKET WHERE TICKSUB=v_user;
    DELETE FROM BEFRIENDS WHERE GAME_USER_UNAME=v_user;
    DELETE FROM BEFRIENDS WHERE GAME_USER_UNAME1=v_user;
    DELETE FROM ACTIVE WHERE ACTUSER=v_user;
    DELETE FROM GAME_USER WHERE UNAME=v_user;
END DELETE_GAMEUSER;
/
CREATE OR REPLACE TRIGGER DELETE_REQUEST
BEFORE DELETE ON REQUEST
FOR EACH ROW
DECLARE
v_id REQUEST.REQID%TYPE;
BEGIN
    v_id:=:OLD.REQID;
    DELETE FROM DEVELOPS_REQUEST WHERE REQUEST_REQID=v_id;
END DELETE_REQUEST;
/
CREATE OR REPLACE TRIGGER EMAILINSERTTRIG
BEFORE INSERT ON GT_USER
FOR EACH ROW
DECLARE
v_email GT_USER.UEMAIL%TYPE;
BEGIN
v_email:=:NEW.UEMAIL;
v_email:=LOWER(v_email);
:NEW.UEMAIL:=v_email;
END EMAILINSERTTRIG;
/
CREATE OR REPLACE TRIGGER GROUPDELTRIG
BEFORE DELETE ON GAME_GROUP
FOR EACH ROW
DECLARE
v_grid GAME_GROUP.GRID%TYPE;
v_grgm GAME_GROUP.GRGM%TYPE;
BEGIN
v_grid:=:OLD.GRID;
v_grgm:=:OLD.GRGM;
DELETE FROM PARTICIPATING_IN WHERE GROUP_GRID = v_grid;
DELETE FROM GROUP_INVITE WHERE GROUP_ID=v_grid AND GROUP_MASTER=v_grgm;
END GROUPDELTRIG;
/
CREATE OR REPLACE TRIGGER ARC_FKArc_1_Game_User BEFORE
  INSERT OR
  UPDATE OF UNAME ON Game_User FOR EACH ROW DECLARE d INTEGER;
  BEGIN
    SELECT A.USERUT INTO d FROM GT_User A WHERE A.UNAME = :new.UNAME;
    IF (d IS NULL OR d <> 1) THEN
      raise_application_error(-20223,'FK Game_User_User_FK in Table Game_User violates Arc constraint on Table GT_User - discriminator column USERUT doesn''t have value 1');
    END IF;
  EXCEPTION
  WHEN NO_DATA_FOUND THEN
    NULL;
  WHEN OTHERS THEN
    RAISE;
  END;
  /
CREATE OR REPLACE TRIGGER ARC_FKArc_1_Administrator BEFORE
  INSERT OR
  UPDATE OF UNAME ON Administrator FOR EACH ROW DECLARE d INTEGER;
  BEGIN
    SELECT A.USERUT INTO d FROM GT_User A WHERE A.UNAME = :new.UNAME;
    IF (d IS NULL OR d <> 0) THEN
      raise_application_error(-20223,'FK Administrator_User_FK in Table Administrator violates Arc constraint on Table GT_User - discriminator column USERUT doesn''t have value 0');
    END IF;
  EXCEPTION
  WHEN NO_DATA_FOUND THEN
    NULL;
  WHEN OTHERS THEN
    RAISE;
  END;
/
CREATE OR REPLACE PACKAGE ACHRANKPKG IS
    TYPE ach_game IS RECORD (
        us PLAYS.GAME_USER_UNAME%TYPE,
        game PLAYS.GAME_GAMEID%TYPE
    );
    TYPE tab_ach IS TABLE OF ach_game INDEX BY BINARY_INTEGER;
    glob_tab tab_ach;
    glob_count BINARY_INTEGER:=0;
    PROCEDURE SET_ACH_RANK(p_name IN VARCHAR2, p_id IN INTEGER);
END ACHRANKPKG;
/
CREATE OR REPLACE PACKAGE BODY ACHRANKPKG IS
    PROCEDURE SET_ACH_RANK(p_name IN VARCHAR2, p_id IN INTEGER) IS
    v_sec INTEGER;
    v_min INTEGER;
    v_hour INTEGER;
    v_day INTEGER;
    v_contains INTEGER:=0;
    v_count INTEGER:=0;
    v_seq INTEGER:=0;
    BEGIN
        SELECT sum(PTDAY), sum(PTHOUR), sum(PTMIN), sum(PTSEC) INTO v_day, v_hour, v_min, v_sec  FROM PLAYS WHERE GAME_USER_UNAME = p_name AND GAME_GAMEID=p_id group by GAME_USER_UNAME, GAME_GAMEID;
        WHILE v_sec >= 60 LOOP
            v_sec:=v_sec-60;
            v_min:=v_min+1;
        END LOOP;
        
        WHILE v_min >= 60 LOOP
            v_min:=v_min-60;
            v_hour:=v_hour+1;
        END LOOP;
        
        WHILE v_day > 0 LOOP
            v_day:=v_day-1;
            v_hour:=v_hour+24;
        END LOOP;
        
        IF v_hour >= 0 THEN
            SELECT RANKID INTO v_seq FROM RANK WHERE RANKNAME='Unranked';
            SELECT count(*) INTO v_contains FROM RANKS WHERE GAME_USER_UNAME = p_name AND GAME_GAMEID=p_id AND RANK_RANKID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO RANKS VALUES (p_id, p_name, SYSDATE, SYSTIMESTAMP,v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 5 THEN
            SELECT RANKID INTO v_seq FROM RANK WHERE RANKNAME='Newbie';
            SELECT count(*) INTO v_contains FROM RANKS WHERE GAME_USER_UNAME = p_name AND GAME_GAMEID=p_id AND RANK_RANKID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO RANKS VALUES (p_id, p_name, SYSDATE, SYSTIMESTAMP,v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 10 THEN
            SELECT RANKID INTO v_seq FROM RANK WHERE RANKNAME='Fresh';
            SELECT count(*) INTO v_contains FROM RANKS WHERE GAME_USER_UNAME = p_name AND GAME_GAMEID=p_id AND RANK_RANKID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO RANKS VALUES (p_id, p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 15 THEN
            SELECT RANKID INTO v_seq FROM RANK WHERE RANKNAME='Rookie';
            SELECT count(*) INTO v_contains FROM RANKS WHERE GAME_USER_UNAME = p_name AND GAME_GAMEID=p_id AND RANK_RANKID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO RANKS VALUES (p_id, p_name, SYSDATE, SYSTIMESTAMP,v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 25 THEN
            SELECT RANKID INTO v_seq FROM RANK WHERE RANKNAME='Junior';
            SELECT count(*) INTO v_contains FROM RANKS WHERE GAME_USER_UNAME = p_name AND GAME_GAMEID=p_id AND RANK_RANKID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO RANKS VALUES (p_id, p_name, SYSDATE, SYSTIMESTAMP,v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 50 THEN
            SELECT RANKID INTO v_seq FROM RANK WHERE RANKNAME='Adept';
            SELECT count(*) INTO v_contains FROM RANKS WHERE GAME_USER_UNAME = p_name AND GAME_GAMEID=p_id AND RANK_RANKID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO RANKS VALUES (p_id, p_name, SYSDATE, SYSTIMESTAMP,v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 80 THEN
            SELECT RANKID INTO v_seq FROM RANK WHERE RANKNAME='Senior';
            SELECT count(*) INTO v_contains FROM RANKS WHERE GAME_USER_UNAME = p_name AND GAME_GAMEID=p_id AND RANK_RANKID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO RANKS VALUES (p_id, p_name, SYSDATE, SYSTIMESTAMP,v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 100 THEN
            SELECT RANKID INTO v_seq FROM RANK WHERE RANKNAME='Master';
           SELECT count(*) INTO v_contains FROM RANKS WHERE GAME_USER_UNAME = p_name AND GAME_GAMEID=p_id AND RANK_RANKID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO RANKS VALUES (p_id, p_name, SYSDATE, SYSTIMESTAMP,v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 200 THEN
            SELECT RANKID INTO v_seq FROM RANK WHERE RANKNAME='Grandmaster';
           SELECT count(*) INTO v_contains FROM RANKS WHERE GAME_USER_UNAME = p_name AND GAME_GAMEID=p_id AND RANK_RANKID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO RANKS VALUES (p_id, p_name, SYSDATE, SYSTIMESTAMP,v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 500 THEN
            SELECT RANKID INTO v_seq FROM RANK WHERE RANKNAME='Elder';
            SELECT count(*) INTO v_contains FROM RANKS WHERE GAME_USER_UNAME = p_name AND GAME_GAMEID=p_id AND RANK_RANKID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO RANKS VALUES (p_id, p_name, SYSDATE, SYSTIMESTAMP,v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        SELECT sum(PTDAY), sum(PTHOUR), sum(PTMIN), sum(PTSEC) INTO v_day, v_hour, v_min, v_sec  FROM PLAYS WHERE GAME_USER_UNAME = p_name group by GAME_USER_UNAME;
        
        WHILE v_sec >= 60 LOOP
            v_sec:=v_sec-60;
            v_min:=v_min+1;
        END LOOP;
        
        WHILE v_min >= 60 LOOP
            v_min:=v_min-60;
            v_hour:=v_hour+1;
        END LOOP;
        
        WHILE v_day > 0 LOOP
            v_day:=v_day-1;
            v_hour:=v_hour+24;
        END LOOP;
        
        IF v_hour >= 10 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='10 Hours';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 25 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='25 Hours';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 50 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='50 Hours';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 100 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='100 Hours';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 200 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='200 Hours';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 300 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='300 Hours';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 400 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='400 Hours';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 500 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='500 Hours';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 1000 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='1000 Hours';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_hour >= 2000 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='2000 Hours';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        SELECT count(DISTINCT GAME_GAMEID) INTO v_count  FROM PLAYS WHERE GAME_USER_UNAME = p_name;
        
        IF v_count >= 5 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='5 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_count >= 10 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='10 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_count >= 20 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='20 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_count >= 30 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='30 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_count >= 40 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='40 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_count >= 50 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='50 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_count >= 60 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='60 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_count >= 70 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='70 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_count >= 80 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='80 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_count >= 90 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='90 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_count >= 100 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='100 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_count >= 200 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='200 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
        
        IF v_count >= 500 THEN
            SELECT ACHID INTO v_seq FROM ACHIEVEMENT WHERE ACHNAME='500 Games';
            SELECT count(*) INTO v_contains FROM EARNED WHERE GAME_USER_UNAME = p_name AND ACHIEVEMENT_ACHID=v_seq;
            IF v_contains=0 THEN
                INSERT INTO EARNED VALUES (p_name, SYSDATE, SYSTIMESTAMP, v_seq);
            END IF;
        END IF;
        v_contains:=0;
            
    END SET_ACH_RANK;
END ACHRANKPKG;
/
CREATE OR REPLACE TRIGGER AchMT1
  BEFORE INSERT OR UPDATE
  ON PLAYS
  BEGIN
    ACHRANKPKG.glob_tab.DELETE;
    ACHRANKPKG.glob_count:=0;
  END AchMT1;
/
CREATE OR REPLACE TRIGGER AchMT2
  BEFORE INSERT OR UPDATE
  ON PLAYS
  FOR EACH ROW
    
  BEGIN
    ACHRANKPKG.glob_count:=ACHRANKPKG.glob_count+1;
    ACHRANKPKG.glob_tab(ACHRANKPKG.glob_count).us:=:NEW.GAME_USER_UNAME;
    ACHRANKPKG.glob_tab(ACHRANKPKG.glob_count).game:=:NEW.GAME_GAMEID;
  END AchMT2;
  /
CREATE OR REPLACE TRIGGER ACH_RANK_TRIGGER
AFTER INSERT OR UPDATE
ON PLAYS
DECLARE
    v_count BINARY_INTEGER:=0;
BEGIN
    v_count:=ACHRANKPKG.glob_tab.FIRST;
    WHILE v_count<=ACHRANKPKG.glob_tab.LAST LOOP
        ACHRANKPKG.SET_ACH_RANK(ACHRANKPKG.glob_tab(v_count).us, ACHRANKPKG.glob_tab(v_count).game);
        v_count:=ACHRANKPKG.glob_tab.NEXT(v_count);
    END LOOP;
END ACH_RANK_TRIGGER;
/
CREATE OR REPLACE PACKAGE GameTimePkg IS
    TYPE t_tip_time IS RECORD (
        g_sec INTEGER,
        g_min INTEGER,
        g_hour INTEGER,
        g_day INTEGER,
        g_id INTEGER
    );
    TYPE t_tabela_time IS TABLE OF t_tip_time INDEX BY BINARY_INTEGER;
    time_count BINARY_INTEGER:=0;
    TYPE t_tip_count IS RECORD (
        g_id INTEGER,
        g_count INTEGER:=0
    );
    TYPE t_tabela_count IS TABLE OF t_tip_count INDEX BY BINARY_INTEGER;
    count_count BINARY_INTEGER:=0;
    v_count t_tabela_count;
    v_time t_tabela_time;
    FUNCTION COMPARE_GAME_TIME(p_id IN INTEGER) RETURN BOOLEAN;
    FUNCTION COMPARE_GAME_COUNT(p_id IN INTEGER) RETURN BOOLEAN;
    PROCEDURE SET_GAME_COUNT(p_id IN INTEGER);
    PROCEDURE SET_GAME_TIME(p_id IN INTEGER);
	PROCEDURE REMOVEPLAYTIME(p_id IN INTEGER, p_sec IN INTEGER, p_min IN INTEGER, p_hour IN INTEGER, p_day IN INTEGER);
END GameTimePkg;
/
CREATE OR REPLACE PACKAGE BODY GameTimePkg IS
 FUNCTION COMPARE_GAME_TIME(p_id IN INTEGER) RETURN BOOLEAN IS
 i_sec INTEGER:=0;
 i_min INTEGER:=0;
 i_hour INTEGER:=0;
 i_day INTEGER:=0;
 i_game_sec INTEGER:=0;
 i_game_min INTEGER:=0;
 i_game_hour INTEGER:=0;
 i_game_day INTEGER:=0;
 BEGIN
    FOR game IN (SELECT * FROM plays where GAME_GAMEID=p_id)
    LOOP
        i_day:=i_day+game.PTDAY;
        i_hour:=i_hour+game.PTHOUR;
        i_min:=i_min+game.PTMIN;
        i_sec:=i_sec+game.PTSEC;
    END LOOP;
    
    WHILE i_sec>60 LOOP
        i_sec:=i_sec-60;
        i_min:=i_min+1;
    END LOOP;
    WHILE i_min>60 LOOP
        i_min:=i_min-60;
        i_hour:=i_hour+1;
    END LOOP;
    WHILE i_hour>24 LOOP
        i_hour:=i_hour-24;
        i_day:=i_day+1;
    END LOOP;
    SELECT GAMEPSEC, GAMEPMIN, GAMEPHOUR,GAMEPDAY INTO i_game_sec, i_game_min, i_game_hour, i_game_day FROM GAME WHERE GAMEID=p_id;
    IF ( i_game_sec=i_sec AND i_game_min=i_min AND i_game_hour=i_hour and i_game_day=i_day) THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END COMPARE_GAME_TIME;

PROCEDURE SET_GAME_TIME(p_id IN INTEGER) IS
 i_sec INTEGER:=0;
 i_min INTEGER:=0;
 i_hour INTEGER:=0;
 i_day INTEGER:=0;
 BEGIN
    FOR game IN (SELECT * FROM plays where GAME_GAMEID=p_id)
    LOOP
        i_day:=i_day+game.PTDAY;
        i_hour:=i_hour+game.PTHOUR;
        i_min:=i_min+game.PTMIN;
        i_sec:=i_sec+game.PTSEC;
    END LOOP;
    
    WHILE i_sec>=60 LOOP
        i_sec:=i_sec-60;
        i_min:=i_min+1;
    END LOOP;
    WHILE i_min>=60 LOOP
        i_min:=i_min-60;
        i_hour:=i_hour+1;
    END LOOP;
    WHILE i_hour>=24 LOOP
        i_hour:=i_hour-24;
        i_day:=i_day+1;
    END LOOP;
    UPDATE GAME SET GAMEPSEC=i_sec, GAMEPMIN=i_min, GAMEPHOUR=i_hour, GAMEPDAY=i_day WHERE GAMEID=p_id;
END SET_GAME_TIME;

FUNCTION COMPARE_GAME_COUNT(p_id IN INTEGER) RETURN BOOLEAN IS
    i_count INTEGER:=0;
    i_game_count INTEGER:=0;
BEGIN
    SELECT COUNT(*) INTO i_count FROM PLAYS where GAME_GAMEID=p_id;
    SELECT GAMEPC INTO i_game_count FROM GAME WHERE GAMEID=p_id;
    IF i_count=i_game_count THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END COMPARE_GAME_COUNT;

PROCEDURE SET_GAME_COUNT(p_id IN INTEGER) IS
    i_count INTEGER:=0;
BEGIN
    SELECT COUNT(*) INTO i_count FROM PLAYS where GAME_GAMEID=p_id;
    UPDATE GAME SET GAMEPC=i_count WHERE GAMEID=p_id;
END SET_GAME_COUNT;

PROCEDURE REMOVEPLAYTIME(p_id IN INTEGER, p_sec IN INTEGER, p_min IN INTEGER, p_hour IN INTEGER, p_day IN INTEGER) IS
v_sec INTEGER;
v_min INTEGER;
v_hour INTEGER;
v_day INTEGER;
v_pc INTEGER;
BEGIN
    SELECT GAMEPSEC, GAMEPMIN, GAMEPHOUR, GAMEPDAY, GAMEPC INTO v_sec, v_min, v_hour, v_day, v_pc FROM GAME WHERE GAMEID=p_id;
    IF v_sec < p_sec THEN
        v_sec:=v_sec+60;
        v_min:= v_min -1;
    END IF;
        v_sec:=v_sec-p_sec;
        
    IF v_min < p_min THEN
        v_min:=v_min+60;
        v_hour:=v_hour-1;
    END IF;
        v_min:=v_min-p_min;
    
    IF v_hour < p_hour THEN
        v_hour:=v_hour+24;
        v_day:=v_day-1;
    END IF;
        v_hour:=v_hour-p_hour;
    
    v_day:=v_day-p_day;
    v_pc:=v_pc-1;
    UPDATE  GAME SET GAMEPC=v_pc, GAMEPSEC=v_sec, GAMEPMIN=v_min, GAMEPHOUR=v_hour, GAMEPDAY=v_day WHERE GAMEID=p_id;
END REMOVEPLAYTIME;

END GameTimePkg;
/
CREATE OR REPLACE TRIGGER GAME_COUNT_TOTAL
AFTER INSERT OR UPDATE
ON PLAYS
DECLARE
    v_count_count BINARY_INTEGER:=0;
	v_time_count BINARY_INTEGER:=0;
BEGIN
    v_count_count:=GAMETIMEPKG.v_count.FIRST;
    WHILE v_count_count<=GameTimePkg.v_count.LAST LOOP
        IF NOT (GAMETIMEPKG.COMPARE_GAME_COUNT(GameTimePkg.v_count(v_count_count).g_id)) THEN
            GAMETIMEPKG.SET_GAME_COUNT(GameTimePkg.v_count(v_count_count).g_id);
        END IF;
        v_count_count:=GameTimePkg.v_count.NEXT(v_count_count);
    END LOOP;
	v_time_count:=GAMETIMEPKG.v_time.FIRST;
    WHILE v_time_count<=GameTimePkg.v_time.LAST LOOP
        IF NOT (GAMETIMEPKG.COMPARE_GAME_TIME(GameTimePkg.v_time(v_time_count).g_id)) THEN
            GAMETIMEPKG.SET_GAME_TIME(GameTimePkg.v_time(v_time_count).g_id);
        END IF;
        v_time_count:=GameTimePkg.v_time.next(v_time_count);
    END LOOP;
END GAME_COUNT_TOTAL;
/

CREATE OR REPLACE TRIGGER TimeCountMT1
  BEFORE INSERT OR UPDATE
  ON PLAYS
  BEGIN
    GameTimePkg.v_time.DELETE;
    GameTimePkg.v_count.DELETE;
    GameTimePkg.count_count:=0;
    GameTimePkg.time_count := 0;
  END TimeCountMT1;
  
/
CREATE OR REPLACE TRIGGER TimeCountMT2
  BEFORE INSERT OR UPDATE
  ON PLAYS
  FOR EACH ROW
  DECLARE
    v_t GameTimePkg.t_tip_time;
    v_c GameTimePkg.t_tip_count;
    v_counter BINARY_INTEGER:=0;
    v_check INTEGER:=0;
  BEGIN
    
    v_t.g_sec := :NEW.PTSEC;
    v_t.g_min := :NEW.PTMIN;
    v_t.g_hour := :NEW.PTHOUR;
    v_t.g_day := :NEW.PTDAY;
    v_t.g_id := :NEW.GAME_GAMEID;
    GameTimePkg.time_count:=GameTimePkg.time_count+1;
    GameTimePkg.v_time(GameTimePkg.time_count):=v_t;
    
    v_counter := GameTimePkg.v_count.FIRST;
    WHILE v_counter<=GameTimePkg.v_count.LAST LOOP
        IF GameTimePkg.v_count(v_counter).g_id=:NEW.GAME_GAMEID THEN
            GameTimePkg.v_count(v_counter).g_count:=GameTimePkg.v_count(v_counter).g_count+1;
            v_check:=1;
        END IF;
        v_counter:=v_counter+1;
    END LOOP;
    IF v_check=0 THEN
        v_c.g_id:=:NEW.GAME_GAMEID;
        v_c.g_count:=0;
        GameTimePkg.count_count:=GameTimePkg.count_count+1;
        GameTimePkg.v_count(GameTimePkg.count_count):=v_c;
    ELSE
        v_check:=0;
    END IF;
  END TimeCountMT2;
/
CREATE OR REPLACE TRIGGER UserGuide 
BEFORE INSERT ON GUIDE
FOR EACH ROW
DECLARE
    v_count INTEGER:=0;
    v_user VARCHAR2(30):=0;
	v_game INTEGER:=0;
BEGIN
    v_user:=:NEW.GAME_USER_UNAME;
	v_game:=:NEW.GAME_GAMEID;
    SELECT count(*) INTO v_count FROM PLAYS WHERE GAME_USER_UNAME=v_user AND GAME_GAMEID=v_game;
    IF v_count=0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'You must play game atleast once to be able to post a guide');
    END IF;
END UserGuide;
/
CREATE OR REPLACE TRIGGER UserReview 
BEFORE INSERT ON REVIEW
FOR EACH ROW
DECLARE
    v_count INTEGER:=0;
    v_user VARCHAR2(30):=0;
    v_type INTEGER:=0;
BEGIN
    v_user:=:NEW.USER_UNAME;
    SELECT count(*) INTO v_count FROM PLAYS WHERE GAME_USER_UNAME=v_user;
    SELECT USERUT INTO v_type FROM GT_USER WHERE UNAME=v_user;
    IF (v_count=0 AND v_type=1) THEN
        RAISE_APPLICATION_ERROR(-20002, 'You must play game atleast once to be able to post a review');
    END IF;
END UserReview;
/
CREATE OR REPLACE TRIGGER DELETEPLAY
BEFORE DELETE 
ON PLAYS
FOR EACH ROW
BEGIN
    GameTimePkg.REMOVEPLAYTIME(:OLD.GAME_GAMEID, :OLD.PTSEC, :OLD.PTMIN, :OLD.PTHOUR, :OLD.PTDAY);
END DELETEPLAY;
/
CREATE OR REPLACE PACKAGE developPkg IS
    PROCEDURE IncrementCount(p_id IN GAME_DEVELOPER.GDID%TYPE);
    Procedure DecrementCount(p_id IN GAME_DEVELOPER.GDID%TYPE);
	FUNCTION GET_DEVELOPED_GAMES(p_id GAME_DEVELOPER.GDID%TYPE) RETURN SYS_REFCURSOR;
END developPkg;
/
CREATE OR REPLACE PACKAGE BODY developPkg IS
    PROCEDURE IncrementCount(p_id IN GAME_DEVELOPER.GDID%TYPE) IS
        v_count GAME_DEVELOPER.GDCOUNT%TYPE;
    BEGIN
        SELECT GDCOUNT INTO v_count FROM GAME_DEVELOPER WHERE GDID=p_id;
        v_count:=v_count+1;
        UPDATE GAME_DEVELOPER SET GDCOUNT=v_count WHERE GDID=p_id;
    END IncrementCount;
    
    PROCEDURE DecrementCount(p_id IN GAME_DEVELOPER.GDID%TYPE) IS
        v_count GAME_DEVELOPER.GDCOUNT%TYPE;
    BEGIN
        SELECT GDCOUNT INTO v_count FROM GAME_DEVELOPER WHERE GDID=p_id;
        v_count:=v_count-1;
        UPDATE GAME_DEVELOPER SET GDCOUNT=v_count WHERE GDID=p_id;
    END DecrementCount;    
	
	FUNCTION GET_DEVELOPED_GAMES(p_id GAME_DEVELOPER.GDID%TYPE) RETURN SYS_REFCURSOR IS
        v_cursor SYS_REFCURSOR;
    BEGIN
    OPEN v_cursor FOR SELECT * FROM GAME WHERE GAMEID IN (SELECT GAME_GAMEID FROM DEVELOPS WHERE GAME_DEVELOPER_GDID=p_id) ORDER BY GAMENAME ASC;
    
        RETURN v_cursor;
    END GET_DEVELOPED_GAMES;

END developPkg;
/
CREATE OR REPLACE TRIGGER DEVELOP_INSTRIG
BEFORE INSERT ON DEVELOPS
FOR EACH ROW
BEGIN
    developPkg.IncrementCount(:NEW.GAME_DEVELOPER_GDID);
END DEVELOP_INSTRIG;
/
CREATE OR REPLACE TRIGGER DEVELOP_DELTRIG
BEFORE DELETE ON DEVELOPS
FOR EACH ROW
BEGIN
    developPkg.DecrementCount(:OLD.GAME_DEVELOPER_GDID);
END DEVELOP_DELTRIG;
/
CREATE OR REPLACE PACKAGE EVENTPKG IS
    FUNCTION GET_TOP_PLAYERS(p_id GAME.GAMEID%TYPE) RETURN SYS_REFCURSOR;
    FUNCTION GET_TOP_GAMES(p_uname GT_USER.UNAME%TYPE) RETURN SYS_REFCURSOR;
	FUNCTION GET_PLAYED_GAMES(p_uname GT_USER.UNAME%TYPE) RETURN SYS_REFCURSOR;
	FUNCTION GET_LATEST_ACHIEVEMENTS(p_uname GAME_USER.UNAME%TYPE) RETURN SYS_REFCURSOR;
	FUNCTION GET_FRIENDS_IN_GAME(p_uname GT_USER.UNAME%TYPE) RETURN SYS_REFCURSOR;
END EVENTPKG;
/
CREATE OR REPLACE PACKAGE BODY EVENTPKG IS
    FUNCTION GET_TOP_PLAYERS(p_id GAME.GAMEID%TYPE) RETURN SYS_REFCURSOR IS
        v_cursor SYS_REFCURSOR;
    BEGIN
		OPEN v_cursor FOR SELECT USERNAME, GAME, PLAY_DAY, PLAY_HOUR, PLAY_MIN, PLAY_SEC, UAVAT as AVATAR FROM GT_USER gt INNER JOIN (SELECT * FROM (SELECT GAME_USER_UNAME as username, game_gameid as game, sum(PTDAY) as play_day, sum(PTHOUR) as play_hour, sum(PTMIN) as play_min, sum(PTSEC) as play_sec FROM PLAYS WHERE GAME_GAMEID=p_id GROUP BY GAME_USER_UNAME, game_gameid ORDER BY sum(PTDAY) DESC, sum(PTHOUR) DESC, sum(PTMIN) DESC, sum(PTSEC) DESC) WHERE ROWNUM <=10) clg on gt.UNAME=clg.USERNAME;
    
        RETURN v_cursor;
    END GET_TOP_PLAYERS;
	
	 FUNCTION GET_PLAYED_GAMES(p_uname GT_USER.UNAME%TYPE) RETURN SYS_REFCURSOR IS
        v_cursor SYS_REFCURSOR;
    BEGIN
		OPEN v_cursor FOR SELECT GAMEID, GAMENAME, GAMERY, PLAY_DAY, PLAY_HOUR, PLAY_MIN, PLAY_SEC, GIMAGE, RANKNAME FROM (SELECT GAME as GAMEID, GAMENAME, GAMERY, PLAY_DAY, PLAY_HOUR, PLAY_MIN, PLAY_SEC, GIMG as GIMAGE FROM GAME gm INNER JOIN (SELECT  game_gameid as game, sum(PTDAY) as play_day, sum(PTHOUR) as play_hour, sum(PTMIN) as play_min, sum(PTSEC) as play_sec FROM PLAYS WHERE GAME_USER_UNAME=p_uname GROUP BY GAME_USER_UNAME, game_gameid ORDER BY sum(PTDAY) DESC, sum(PTHOUR) DESC, sum(PTMIN) DESC, sum(PTSEC) DESC) clg on gm.GAMEID=clg.game) playedGame, RANKS rs, RANK rk WHERE playedGame.GAMEID=rs.GAME_GAMEID AND rk.RANKID=rs.RANK_RANKID AND rs.GAME_USER_UNAME=p_uname AND rk.rankh >= ALL(SELECT ran.rankh FROM RANK ran, RANKS ras WHERE ran.rankid=ras.rank_rankid AND ras.game_user_uname=p_uname AND ras.game_gameid=playedgame.GAMEID);
    
		RETURN v_cursor;
    END GET_PLAYED_GAMES;
    
    FUNCTION GET_TOP_GAMES(p_uname GT_USER.UNAME%TYPE) RETURN SYS_REFCURSOR IS
        v_cursor SYS_REFCURSOR;
    BEGIN
		OPEN v_cursor FOR SELECT GAME as GAMEID, GAMENAME, GAMERY, PLAY_DAY, PLAY_HOUR, PLAY_MIN, PLAY_SEC, GIMG as GIMAGE FROM GAME gm INNER JOIN (SELECT * FROM (SELECT  game_gameid as game, sum(PTDAY) as play_day, sum(PTHOUR) as play_hour, sum(PTMIN) as play_min, sum(PTSEC) as play_sec FROM PLAYS WHERE GAME_USER_UNAME=p_uname GROUP BY GAME_USER_UNAME, game_gameid ORDER BY sum(PTDAY) DESC, sum(PTHOUR) DESC, sum(PTMIN) DESC, sum(PTSEC) DESC) WHERE ROWNUM <=10) clg on gm.GAMEID=clg.game;
    
		RETURN v_cursor;
    END GET_TOP_GAMES;
	
	FUNCTION GET_LATEST_ACHIEVEMENTS(p_uname GAME_USER.UNAME%TYPE) RETURN SYS_REFCURSOR IS
		v_cursor SYS_REFCURSOR;
	BEGIN
		OPEN v_cursor FOR SELECT * FROM (SELECT ACHNAME, ACHCOND, ACHID FROM ACHIEVEMENT a, EARNED e where a.achid=e.ACHIEVEMENT_ACHID and GAME_USER_UNAME=p_uname ORDER BY EARNDATE DESC, EARNTIME DESC) WHERE ROWNUM<=5;
		RETURN v_cursor;
	END GET_LATEST_ACHIEVEMENTS;
	
	FUNCTION GET_FRIENDS_IN_GAME(p_uname GT_USER.UNAME%TYPE) RETURN SYS_REFCURSOR IS
        v_cursor SYS_REFCURSOR;
    BEGIN
		OPEN v_cursor FOR SELECT GAMEID, GAMENAME, GAMERY, UNAME, UAVAT FROM (SELECT * FROM BEFRIENDS INNER JOIN (SELECT gm.GAMEID, gm.GAMENAME, gm.GAMERY, gm.GIMG, gt.UNAME, gt.UAVAT FROM GAME gm, GAME_USER gu, GT_USER gt WHERE gm.gameid=gu.playing_game_id AND gt.uname=gu.uname AND gu.playing=1) tmp ON GAME_USER_UNAME=tmp.UNAME WHERE GAME_USER_UNAME1=p_uname);
    
        RETURN v_cursor;
    END GET_FRIENDS_IN_GAME;
END EVENTPKG;
/
CREATE OR REPLACE PACKAGE FORUMPKG IS
    PROCEDURE EDIT_TOPIC(p_tid IN TOPIC.TPID%TYPE, p_date IN POST.POSTDATE%TYPE);
    PROCEDURE INCREASE_TOPIC(p_tid IN TOPIC.TPID%TYPE, p_date IN POST.POSTDATE%TYPE);
	FUNCTION GET_MESSAGES(p_uname1 GT_USER.UNAME%TYPE, p_uname2 GT_USER.UNAME%TYPE) RETURN SYS_REFCURSOR;
END FORUMPKG;
/
CREATE OR REPLACE PACKAGE BODY FORUMPKG IS
    PROCEDURE EDIT_TOPIC(p_tid IN TOPIC.TPID%TYPE, p_date IN POST.POSTDATE%TYPE) IS
    BEGIN
        UPDATE TOPIC SET TPLMD=p_date WHERE TPID=p_tid;
    END EDIT_TOPIC;
    PROCEDURE INCREASE_TOPIC(p_tid IN TOPIC.TPID%TYPE, p_date IN POST.POSTDATE%TYPE) IS
    v_count TOPIC.TPPC%TYPE:=0;
    BEGIN
        SELECT TPPC INTO v_count FROM TOPIC WHERE TPID=p_tid;
        IF v_count!=0 THEN
            v_count:=v_count+1;
            UPDATE TOPIC SET TPLMD=p_date, TPPC=v_count WHERE TPID=p_tid;
        ELSE
            v_count:=v_count+1;
            UPDATE TOPIC SET TPLMD=p_date, TPPC=v_count, TPOD=p_date WHERE TPID=p_tid;
        END IF;
    END INCREASE_TOPIC;
	FUNCTION GET_MESSAGES(p_uname1 GT_USER.UNAME%TYPE, p_uname2 GT_USER.UNAME%TYPE) RETURN SYS_REFCURSOR IS
		v_cursor SYS_REFCURSOR;
	BEGIN
		OPEN v_cursor FOR SELECT MSGDATE, MSGSENT, MSGCONT, MSGTIME, USER_UNAME AS MSGREC FROM MESSAGE msg INNER JOIN RECEIVED rec on rec.MESSAGE_MSGDATE=msg.MSGDATE AND rec.MESSAGE_MSGSENT=msg.MSGSENT AND rec.MESSAGE_MSGTIME=msg.MSGTIME WHERE (USER_UNAME=p_uname1 OR USER_UNAME=p_uname2) AND (MSGSENT=p_uname1 OR MSGSENT=p_uname2) ORDER BY MSGDATE ASC, MSGTIME ASC;
		RETURN v_cursor;
	END GET_MESSAGES;
END FORUMPKG;
/
CREATE OR REPLACE TRIGGER ON_POST_TRIG
BEFORE INSERT OR UPDATE ON POST
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        FORUMPKG.INCREASE_TOPIC(:NEW.POSTTID, :NEW.POSTDATE);
    ELSIF UPDATING THEN
        FORUMPKG.EDIT_TOPIC(:NEW.POSTTID, :NEW.POSTDATE);
    END IF;
END ON_POST_TRIG;
/
CREATE OR REPLACE PACKAGE recommendPkg IS
fail_five INTEGER:=0;
fail_ten INTEGER:=0;
fail_fifty INTEGER:=0;
fail_hundred INTEGER:=0;
fail_similar INTEGER:=0;
FUNCTION RECOMMEND_GAMES(p_uname IN VARCHAR2, p_sample IN INTEGER) RETURN INTEGER;
FUNCTION RECOMMEND_SIMILAR_NAME(p_uname IN VARCHAR2) RETURN INTEGER;
FUNCTION RECOMMEND_RANDOM(p_uname IN VARCHAR2) RETURN INTEGER;
PROCEDURE FULL_RECOMMEND(p_id IN INTEGER);
FUNCTION GET_RECOMMENDED(p_uname GAME_USER.UNAME%TYPE) RETURN SYS_REFCURSOR;
END recommendPkg;
/
CREATE OR REPLACE PACKAGE BODY recommendPkg IS
FUNCTION RECOMMEND_GAMES(p_uname IN VARCHAR2, p_sample IN INTEGER) RETURN INTEGER IS
TYPE tab_games IS TABLE OF GAME%ROWTYPE INDEX BY BINARY_INTEGER;
i BINARY_INTEGER:=0;
TYPE rec_users IS RECORD (
    username GT_USER.UNAME%TYPE,
    cnt INTEGER:=0
);
TYPE tab_users IS TABLE OF rec_users INDEX BY BINARY_INTEGER;
j BINARY_INTEGER:=0;
v_game_counter INTEGER:=0;
v_game GAME%ROWTYPE;
v_tab tab_games;
v_user rec_users;
v_users tab_users;
v_check INTEGER:=0;
v_played INTEGER:=1;
v_exit INTEGER:=0;
v_game_id INTEGER;

BEGIN
    v_game_counter:=1;
    FOR played_game in (SELECT GAME_USER_UNAME, GAME_GAMEID, sum(PTDAY), sum(PTHOUR), sum(PTMIN), sum(PTSEC)  FROM PLAYS WHERE GAME_USER_UNAME=p_uname group by GAME_USER_UNAME, GAME_GAMEID  Order by sum(PTDAY) DESC, sum(PTHOUR) DESC, sum(PTMIN) DESC, sum(PTSEC) DESC) LOOP
        SELECT * INTO v_game FROM GAME WHERE GAMEID=played_game.GAME_GAMEID;
        i:=i+1;
        v_tab(i):=v_game;
        EXIT WHEN i=p_sample;
    END LOOP;

    WHILE v_game_counter<=i LOOP
        v_game:=v_tab(v_game_counter);
        FOR username IN (SELECT DISTINCT GAME_USER_UNAME FROM PLAYS WHERE GAME_GAMEID=v_game.GAMEID AND GAME_USER_UNAME!=p_uname ) LOOP
            IF (v_users.FIRST IS NOT NULL) THEN
                j:=v_users.FIRST;
            END IF;
            IF (v_users.LAST IS NOT NULL) THEN
                WHILE j<=v_users.LAST LOOP
                    IF (v_users(j).username=username.GAME_USER_UNAME) THEN
                        v_check:=1;
                        v_users(j).cnt:=v_users(j).cnt+1;
                    END IF;
                    j:=v_users.NEXT(j);
                    EXIT WHEN v_check=1;
                END LOOP;
            END IF;
            IF v_check=0 THEN
                IF (v_users.LAST IS NOT NULL) THEN
                    j:=v_users.LAST;
                END IF;
                j:=j+1;
                v_user.username:=username.GAME_USER_UNAME;
                v_user.cnt:=1;
                v_users(j):=v_user;
            END IF;
            v_check:=0;
         END LOOP;
         v_game_counter:=v_game_counter+1;
        END LOOP;

        WHILE (v_users.COUNT!=0 AND v_game_id IS NULL) LOOP
            j:=v_users.FIRST;
            v_user.cnt:=0;
            v_user.username:='';
            WHILE j<=v_users.LAST LOOP
                IF v_users(j).cnt >= v_user.cnt THEN
                    v_user.username:= v_users(j).username;
                    v_user.cnt:=v_users(j).cnt;
                END IF;
               j:=v_users.NEXT(j);
            END LOOP;
            
            j:=v_users.FIRST;
            WHILE j<=v_users.LAST LOOP
                IF v_users(j).username =  v_user.username  THEN
                    v_users.DELETE(j);
                END IF;
               j:=v_users.NEXT(j);
            END LOOP;
            v_tab.DELETE;
            i:=0;
            FOR played_game in (SELECT GAME_USER_UNAME, GAME_GAMEID, sum(PTDAY), sum(PTHOUR), sum(PTMIN), sum(PTSEC)  FROM PLAYS WHERE GAME_USER_UNAME = v_user.username group by GAME_USER_UNAME, GAME_GAMEID  Order by sum(PTDAY) DESC, sum(PTHOUR) DESC, sum(PTMIN) DESC, sum(PTSEC) DESC) LOOP
                SELECT * INTO v_game FROM GAME WHERE GAMEID=played_game.GAME_GAMEID;
                i:=i+1;
                v_tab(i):=v_game;
            END LOOP;


            i:=v_tab.FIRST;
            WHILE i<=v_tab.LAST LOOP
                SELECT count(*) INTO v_played FROM PLAYS WHERE GAME_USER_UNAME=p_uname AND GAME_GAMEID=v_tab(i).GAMEID;
                IF v_played=0 THEN
                    v_game_id:=v_tab(i).GAMEID;
                    v_exit:=1;
                    v_played:=1;
                END IF;
                EXIT when v_exit=1;
                i:=v_tab.NEXT(i);
            END LOOP;
        END LOOP;
        IF (v_game_id IS NULL AND fail_five=0) THEN
            fail_five:=1;
            v_game_id:=RECOMMEND_GAMES(p_uname,10);
        ELSIF (v_game_id IS NULL AND fail_ten=0) THEN
            fail_ten:=1;
            v_game_id:=RECOMMEND_GAMES(p_uname,50);
        ELSIF (v_game_id IS NULL AND fail_fifty=0) THEN
            fail_fifty:=1;
            v_game_id:=RECOMMEND_GAMES(p_uname,100);
        ELSIF (v_game_id IS NULL AND fail_hundred=0) THEN
            fail_hundred:=1;
            v_game_id:=RECOMMEND_SIMILAR_NAME(p_uname);
            if (v_game_id IS NULL) THEN
                fail_similar:=1;
            END IF;
        END IF;
        IF (v_game_id IS NULL AND fail_similar=1) THEN
            v_game_id:=RECOMMEND_RANDOM(p_uname);
        END IF;
        IF (v_game_id IS NULL) THEN
            v_game_id:=0;
        END IF;
    RETURN v_game_id;
END RECOMMEND_GAMES;

FUNCTION RECOMMEND_SIMILAR_NAME(p_uname IN VARCHAR2) RETURN INTEGER IS
    TYPE tab_games IS TABLE OF GAME%ROWTYPE INDEX BY BINARY_INTEGER;
    i BINARY_INTEGER:=0;
    j BINARY_INTEGER:=0;
    v_game GAME%ROWTYPE;
    v_tab tab_games;
    v_check INTEGER:=0;
    v_tab_non_played tab_games;
    v_match INTEGER:=0;
    v_matched_game INTEGER;
BEGIN
    FOR played_game in (SELECT GAME_USER_UNAME, GAME_GAMEID, sum(PTDAY), sum(PTHOUR), sum(PTMIN), sum(PTSEC)  FROM PLAYS WHERE GAME_USER_UNAME = p_uname group by GAME_USER_UNAME, GAME_GAMEID  Order by sum(PTDAY) DESC, sum(PTHOUR) DESC, sum(PTMIN) DESC, sum(PTSEC) DESC) LOOP
        SELECT * INTO v_game FROM GAME WHERE GAMEID=played_game.GAME_GAMEID;
        i:=i+1;
        v_tab(i):=v_game;
    END LOOP;
    
    FOR other_game in (SELECT * FROM GAME) LOOP
        i:=v_tab.FIRST;
        WHILE i<=v_tab.LAST LOOP
            if other_game.GAMEID = v_tab(i).GAMEID THEN
                v_check:=1;
            END IF;
            i:=v_tab.NEXT(i);
            EXIT WHEN v_check=1;
        END LOOP;
        if v_check=0 THEN
            j:=j+1;
            v_tab_non_played(j):=other_game;
        ELSE
            v_check:=0;
        END IF;
    END LOOP;
    
    IF (v_tab_non_played.FIRST IS NOT NULL) THEN
        j:=v_tab_non_played.FIRST;
    ELSE 
        RETURN NULL;
    END IF;
    WHILE j<=v_tab_non_played.LAST LOOP
        i:=v_tab.FIRST;
        WHILE i<=v_tab.LAST LOOP
            v_match:=UTL_MATCH.edit_distance_similarity(v_tab(i).GAMENAME, v_tab_non_played(j).GAMENAME);
            IF v_match > 50 THEN
                v_matched_game:=v_tab_non_played(j).GAMEID;
                RETURN v_matched_game;
            END IF;
            i:=v_tab.NEXT(i);
        END LOOP;
        j:=v_tab_non_played.NEXT(j);
    END LOOP;
    
    RETURN NULL;

END RECOMMEND_SIMILAR_NAME;

FUNCTION RECOMMEND_RANDOM(p_uname IN VARCHAR2) RETURN INTEGER IS
    TYPE tab_games IS TABLE OF GAME%ROWTYPE INDEX BY BINARY_INTEGER;
    i BINARY_INTEGER:=0;
    j BINARY_INTEGER:=0;
    v_game GAME%ROWTYPE;
    v_tab tab_games;
    v_check INTEGER:=0;
    v_tab_non_played tab_games;
BEGIN
    FOR played_game in (SELECT GAME_USER_UNAME, GAME_GAMEID, sum(PTDAY), sum(PTHOUR), sum(PTMIN), sum(PTSEC)  FROM PLAYS WHERE GAME_USER_UNAME = p_uname group by GAME_USER_UNAME, GAME_GAMEID  Order by sum(PTDAY) DESC, sum(PTHOUR) DESC, sum(PTMIN) DESC, sum(PTSEC) DESC) LOOP
        SELECT * INTO v_game FROM GAME WHERE GAMEID=played_game.GAME_GAMEID;
        i:=i+1;
        v_tab(i):=v_game;
    END LOOP;
    
    FOR other_game in (SELECT * FROM GAME) LOOP
        i:=v_tab.FIRST;
        WHILE i<=v_tab.LAST LOOP
            if other_game.GAMEID = v_tab(i).GAMEID THEN
                v_check:=1;
            END IF;
            i:=v_tab.NEXT(i);
            EXIT WHEN v_check=1;
        END LOOP;
        if v_check=0 THEN
            j:=j+1;
            v_tab_non_played(j):=other_game;
        ELSE
            v_check:=0;
        END IF;
    END LOOP;
     IF (v_tab_non_played.FIRST IS NOT NULL) THEN
        j:=v_tab_non_played.FIRST;
    ELSE 
        RETURN NULL;
    END IF;
    WHILE j<=v_tab_non_played.LAST LOOP
        RETURN v_tab_non_played(j).GAMEID;
    END LOOP;
    RETURN NULL;
END RECOMMEND_RANDOM;

PROCEDURE FULL_RECOMMEND(p_id IN INTEGER) IS
    v_game GAME%ROWTYPE;
BEGIN
    SELECT * INTO v_game FROM GAME WHERE GAMEID=p_id;
    DBMS_OUTPUT.PUT_LINE('Game recommended is: ' || v_game.GAMENAME);

END FULL_RECOMMEND;

FUNCTION GET_RECOMMENDED(p_uname GAME_USER.UNAME%TYPE) RETURN SYS_REFCURSOR IS
		v_cursor SYS_REFCURSOR;
		v_game INTEGER;
	BEGIN
		v_game:=recommendPkg.RECOMMEND_GAMES(p_uname, 5);
		OPEN v_cursor FOR SELECT * FROM GAME WHERE GAMEID=v_game;
		RETURN v_cursor;
	END GET_RECOMMENDED;

END recommendPkg;
/
CREATE OR REPLACE PACKAGE REPORTPKG IS
    TYPE rec_plays IS RECORD (
        id PLAYS.GAME_GAMEID%TYPE,
        name VARCHAR2(80),
        ptday PLAYS.PTDAY%TYPE,
        pthour PLAYS.PTHOUR%TYPE,
        ptmin PLAYS.PTMIN%TYPE,
        ptsec PLAYS.PTSEC%TYPE,
        demo0 INTEGER:=0,
        demo10 INTEGER:=0,
        demo20 INTEGER:=0,
        demo30 INTEGER:=0
    );
    TYPE tab_plays IS TABLE OF rec_plays INDEX BY BINARY_INTEGER;
    TYPE rec_country IS RECORD (
        country GAME_USER.UCOUNTRY%TYPE,
        games tab_plays
    );
    TYPE tab_country IS TABLE OF rec_country INDEX BY BINARY_INTEGER;
    
    FUNCTION GET_REPORT RETURN tab_country;
    PROCEDURE PRINT_REPORT (p_countries IN tab_country);
    PROCEDURE GAME_AGE_REPORT (gameid IN INTEGER, emp_cursor OUT sys_refcursor);
	PROCEDURE DEVELOPER_GAME_POPULARITY(p_devid IN INTEGER, p_cursor OUT SYS_REFCURSOR);
END REPORTPKG;
/
CREATE OR REPLACE PACKAGE BODY REPORTPKG IS

FUNCTION GET_REPORT RETURN tab_country IS
    
    j BINARY_INTEGER:=1;
    i BINARY_INTEGER:=1;
    v_plays tab_plays;
    v_play rec_plays;
    v_dummy tab_plays;
    v_dummy2 tab_plays;
    v_counter INTEGER:=0;
    v_check INTEGER:=0;
    v_country rec_country;
    v_countries tab_country;
    v_cmp_day INTEGER:=0;
    v_cmp_hour INTEGER:=0;
    v_cmp_min INTEGER:=0;
    v_cmp_sec INTEGER:=0;
    v_delete BINARY_INTEGER:=0;
    BEGIN
        FOR country IN (SELECT DISTINCT UCOUNTRY FROM GAME_USER) LOOP
            FOR gameUser IN (SELECT UNAME, UDOB FROM GAME_USER WHERE UCOUNTRY=country.UCOUNTRY) LOOP
                FOR play IN (SELECT GAME_USER_UNAME, GAME_GAMEID, sum(PTDAY) as play_day, sum(PTHOUR) as play_hour, sum(PTMIN) as play_min, sum(PTSEC) as play_sec FROM PLAYS WHERE GAME_USER_UNAME=gameUser.UNAME GROUP BY GAME_USER_UNAME, GAME_GAMEID ORDER BY sum(PTDAY) DESC, sum(PTHOUR) DESC, sum(PTMIN) DESC, sum(PTSEC) DESC) LOOP
                    IF v_plays.FIRST IS NOT NULL THEN
                        i:=v_plays.FIRST;
                        WHILE i<=v_plays.LAST LOOP
                            IF v_plays(i).id=play.GAME_GAMEID THEN
                                v_plays(i).ptday:=v_plays(i).ptday+play.play_day;
                                v_plays(i).pthour:=v_plays(i).pthour+play.play_hour;
                                v_plays(i).ptmin:=v_plays(i).ptmin+play.play_min;
                                v_plays(i).ptsec:=v_plays(i).ptsec+play.play_sec;
                                IF trunc((sysdate-gameUser.UDOB)/365)<=10 THEN
                                    v_plays(i).demo0:=v_plays(i).demo0+1;
                                ELSIF trunc((sysdate-gameUser.UDOB)/365)<=20 THEN
                                    v_plays(i).demo10:=v_plays(i).demo10+1;
                                ELSIF trunc((sysdate-gameUser.UDOB)/365)<=30 THEN
                                    v_plays(i).demo20:=v_plays(i).demo20+1;
                                ELSE
                                    v_plays(i).demo30:=v_plays(i).demo30+1;
                                END IF;
                                v_check:=1;
                            END IF;
                            i:=i+1;
                            EXIT WHEN v_check=1;
                        END LOOP;
                        IF v_check!=1 THEN
                            v_play.id:=play.GAME_GAMEID;
                            SELECT GAMENAME INTO v_play.name FROM GAME WHERE GAMEID=play.GAME_GAMEID;
                            v_play.ptday:=play.play_day;
                            v_play.pthour:=play.play_hour;
                            v_play.ptmin:=play.play_min;
                            v_play.ptsec:=play.play_sec;
                            v_plays(i):=v_play;
                            IF trunc((sysdate-gameUser.UDOB)/365)<=10 THEN
                                v_plays(i).demo0:=v_plays(i).demo0+1;
                            ELSIF trunc((sysdate-gameUser.UDOB)/365)<=20 THEN
                                v_plays(i).demo10:=v_plays(i).demo10+1;
                            ELSIF trunc((sysdate-gameUser.UDOB)/365)<=30 THEN
                                v_plays(i).demo20:=v_plays(i).demo20+1;
                            ELSE
                                v_plays(i).demo30:=v_plays(i).demo30+1;
                            END IF;
                            i:=i+1;
                        ELSE
                            v_check:=0;
                        END IF;
                    ELSE
                        v_play.id:=play.GAME_GAMEID;
                        SELECT GAMENAME INTO v_play.name FROM GAME WHERE GAMEID=play.GAME_GAMEID;
                        v_play.ptday:=play.play_day;
                        v_play.pthour:=play.play_hour;
                        v_play.ptmin:=play.play_min;
                        v_play.ptsec:=play.play_sec;
                        v_plays(i):=v_play;
                        IF trunc((sysdate-gameUser.UDOB)/365)<=10 THEN
                            v_plays(i).demo0:=v_plays(i).demo0+1;
                        ELSIF trunc((sysdate-gameUser.UDOB)/365)<=20 THEN
                            v_plays(i).demo10:=v_plays(i).demo10+1;
                        ELSIF trunc((sysdate-gameUser.UDOB)/365)<=30 THEN
                            v_plays(i).demo20:=v_plays(i).demo20+1;
                        ELSE
                            v_plays(i).demo30:=v_plays(i).demo30+1;
                        END IF;
                        i:=i+1;
                    END IF;
                    
                END LOOP;
            END LOOP;
            
            v_country.country:=country.UCOUNTRY;
            v_country.games:=v_plays;
            v_countries(j):=v_country;
            j:=j+1;
            v_plays.DELETE;
            i:=1;
            
        END LOOP;
        
        j:=v_countries.FIRST;
        WHILE j<=v_countries.LAST LOOP
            i:=v_countries(j).games.FIRST;
            WHILE i<=v_countries(j).games.LAST LOOP
                WHILE v_countries(j).games(i).ptsec>= 60 LOOP
                    v_countries(j).games(i).ptsec:=v_countries(j).games(i).ptsec-60;
                    v_countries(j).games(i).ptmin:=v_countries(j).games(i).ptmin+1;
                END LOOP;
                
                WHILE v_countries(j).games(i).ptmin>= 60 LOOP
                    v_countries(j).games(i).ptmin:=v_countries(j).games(i).ptmin-60;
                    v_countries(j).games(i).pthour:=v_countries(j).games(i).pthour+1;
                END LOOP;
                
                WHILE v_countries(j).games(i).pthour>= 24 LOOP
                    v_countries(j).games(i).pthour:=v_countries(j).games(i).pthour-24;
                    v_countries(j).games(i).ptday:=v_countries(j).games(i).ptday+1;
                END LOOP;
                
                i:=v_countries(j).games.NEXT(i);
            END LOOP;
            j:=v_countries.NEXT(j);
        END LOOP;

        j:=v_countries.FIRST;
        WHILE j<=v_countries.LAST LOOP
            v_dummy:=v_countries(j).games;
            v_counter:=0;
            WHILE v_dummy.COUNT!=0 LOOP
            
                i:=v_dummy.FIRST;
                WHILE i<=v_dummy.LAST LOOP
                    IF v_dummy(i).ptday > v_cmp_day THEN
                        v_cmp_day:= v_dummy(i).ptday;
                        v_cmp_hour:= v_dummy(i).pthour;
                        v_cmp_min:=v_dummy(i).ptmin;
                        v_cmp_sec:=v_dummy(i).ptsec;
                        v_delete:=i;
                    ELSIF v_dummy(i).ptday = v_cmp_day THEN
                        IF v_dummy(i).pthour > v_cmp_hour THEN
                            v_cmp_day:= v_dummy(i).ptday;
                            v_cmp_hour:= v_dummy(i).pthour;
                            v_cmp_min:=v_dummy(i).ptmin;
                            v_cmp_sec:=v_dummy(i).ptsec;
                            v_delete:=i;
                        ELSIF v_dummy(i).pthour = v_cmp_hour THEN
                            IF v_dummy(i).ptmin > v_cmp_min THEN
                                v_cmp_day:= v_dummy(i).ptday;
                                v_cmp_hour:= v_dummy(i).pthour;
                                v_cmp_min:=v_dummy(i).ptmin;
                                v_cmp_sec:=v_dummy(i).ptsec;
                                v_delete:=i;
                            ELSIF v_dummy(i).ptmin = v_cmp_min THEN
                                IF v_dummy(i).ptsec > v_cmp_sec THEN
                                    v_cmp_day:= v_dummy(i).ptday;
                                    v_cmp_hour:= v_dummy(i).pthour;
                                    v_cmp_min:=v_dummy(i).ptmin;
                                    v_cmp_sec:=v_dummy(i).ptsec;
                                    v_delete:=i;
                                ELSIF v_dummy(i).ptsec = v_cmp_sec THEN
                                    v_cmp_day:= v_dummy(i).ptday;
                                    v_cmp_hour:= v_dummy(i).pthour;
                                    v_cmp_min:=v_dummy(i).ptmin;
                                    v_cmp_sec:=v_dummy(i).ptsec;
                                    v_delete:=i;
                                END IF;
                            END IF;
                        END IF;
                    END IF;
                    i:=v_dummy.NEXT(i);
                END LOOP;
                v_counter:=v_counter+1;
                v_dummy2(v_counter):=v_dummy(v_delete);
                v_dummy.DELETE(v_delete);
                v_cmp_day:=0;
                v_cmp_hour:=0;
                v_cmp_min:=0;
                v_cmp_sec:=0;
                v_delete:=0;
                i:=v_dummy.FIRST;
                EXIT WHEN v_counter=10;
            END LOOP;
            v_countries(j).games:=v_dummy2;
            j:=v_countries.NEXT(j);
        END LOOP;
        
        RETURN v_countries;
END GET_REPORT;
    
PROCEDURE PRINT_REPORT (p_countries IN tab_country) IS
    i BINARY_INTEGER;
    j BINARY_INTEGER;
    v_total INTEGER;
    v_broj NUMBER(38,2);
    BEGIN
        DBMS_OUTPUT.ENABLE(1000000);
        i:=p_countries.FIRST;
        DBMS_OUTPUT.PUT_LINE('######################################## COUNTRY AND AGE STATISTICS REPORT ########################################');
        DBMS_OUTPUT.PUT_LINE(' ');
        WHILE i<=p_countries.LAST LOOP
            DBMS_OUTPUT.PUT_LINE('COUNTRY : ' || p_countries(i).country);
            j:=p_countries(i).games.FIRST;
            WHILE j<=p_countries(i).games.LAST LOOP
                DBMS_OUTPUT.PUT_LINE(CHR(7) || CHR(7) || '#' || j || ' GAME : ' || p_countries(i).games(j).name || ' WITH PLAYTIME: ' || p_countries(i).games(j).ptday || ' days ' || p_countries(i).games(j).pthour || ' hours ' || p_countries(i).games(j).ptmin || ' minutes ' || p_countries(i).games(j).ptsec || ' seconds.');
                v_total:=p_countries(i).games(j).demo0+p_countries(i).games(j).demo10+p_countries(i).games(j).demo20+p_countries(i).games(j).demo30;
                v_broj:= (p_countries(i).games(j).demo0*100)/v_total;
                DBMS_OUTPUT.PUT_LINE(CHR(7) || CHR(7) || CHR(7) || CHR(7) || 'PEOPLE AGED 1-10 MAKE UP ' || v_broj || '% OF DEMOGRAPHICS');
                v_broj:= (p_countries(i).games(j).demo10*100)/v_total;
                DBMS_OUTPUT.PUT_LINE(CHR(7) || CHR(7) || CHR(7) || CHR(7) || 'PEOPLE AGED 11-20 MAKE UP ' || v_broj || '% OF DEMOGRAPHICS');
                v_broj:= (p_countries(i).games(j).demo20*100)/v_total;
                DBMS_OUTPUT.PUT_LINE(CHR(7) || CHR(7) || CHR(7) || CHR(7) || 'PEOPLE AGED 21-30 MAKE UP ' || v_broj || '% OF DEMOGRAPHICS');
                v_broj:= (p_countries(i).games(j).demo30*100)/v_total;
                DBMS_OUTPUT.PUT_LINE(CHR(7) || CHR(7) || CHR(7) || CHR(7) || 'PEOPLE AGED 30 AND ABOVE MAKE UP ' || v_broj || '% OF DEMOGRAPHICS');
                j:=p_countries(i).games.NEXT(j);
            END LOOP;
            i:=p_countries.NEXT(i);
        END LOOP;
    
END PRINT_REPORT;

PROCEDURE GAME_AGE_REPORT(gameid IN INTEGER, emp_cursor OUT sys_refcursor) IS
BEGIN
OPEN emp_cursor for SELECT AGE, NVL(PERCENT,0) AS PERCENT  FROM AGE ag NATURAL LEFT OUTER JOIN (SELECT  CASE 
         WHEN TRUNC(MONTHS_BETWEEN(sysdate, UDOB)/12) <= 10 THEN '1-10' 
         WHEN TRUNC(MONTHS_BETWEEN(sysdate, UDOB)/12) <= 20 THEN '11-20' 
         WHEN TRUNC(MONTHS_BETWEEN(sysdate, UDOB)/12) <= 30 THEN '21-30'
         ELSE '31+' 
       END AS age, cast((Count(UNAME)* 100 / (Select DISTINCT Count(DISTINCT UNAME) From GAME_USER, PLAYS WHERE GAME_USER_UNAME=UNAME AND GAME_GAMEID=gameid)) as decimal(10,2)) as percent FROM (SELECT Distinct GAME_USER_UNAME as uname, UDOB FROM PLAYS p, GAME_USER gu WHERE GAME_GAMEID=gameid AND UNAME=GAME_USER_UNAME) GROUP BY CASE 
           WHEN TRUNC(MONTHS_BETWEEN(sysdate, UDOB)/12) <= 10 THEN '1-10' 
           WHEN TRUNC(MONTHS_BETWEEN(sysdate, UDOB)/12) <= 20 THEN '11-20'
           WHEN TRUNC(MONTHS_BETWEEN(sysdate, UDOB)/12) <= 30 THEN '21-30'
           ELSE '31+' 
         END order by percent desc, age asc) order by percent desc, age asc;

END GAME_AGE_REPORT;

PROCEDURE DEVELOPER_GAME_POPULARITY(p_devid IN INTEGER, p_cursor OUT SYS_REFCURSOR) IS
BEGIN
OPEN p_cursor for SELECT GAMEID, GAMENAME, GAMERY, 
CASE WHEN (SELECT MAX(PLAYTIME) FROM (SELECT (86400*(GAMEPDAY)+3600*(GAMEPHOUR)+60*(GAMEPMIN)+GAMEPSEC) as PLAYTIME FROM GAME_DEVELOPER gd,GAME gm, DEVELOPS dev WHERE gd.GDID=dev.GAME_DEVELOPER_GDID AND dev.GAME_GAMEID=gm.GAMEID AND gd.GDID=p_devid))=0 THEN 0 
ELSE ((86400*(GAMEPDAY)+3600*(GAMEPHOUR)+60*(GAMEPMIN)+GAMEPSEC)/(SELECT MAX(PLAYTIME) FROM (SELECT (86400*(GAMEPDAY)+3600*(GAMEPHOUR)+60*(GAMEPMIN)+GAMEPSEC) as PLAYTIME FROM GAME_DEVELOPER gd,GAME gm, DEVELOPS dev WHERE gd.GDID=dev.GAME_DEVELOPER_GDID AND dev.GAME_GAMEID=gm.GAMEID AND gd.GDID=p_devid))) END as PLAYTIME FROM GAME_DEVELOPER gd,GAME gm, DEVELOPS dev WHERE gd.GDID=dev.GAME_DEVELOPER_GDID AND dev.GAME_GAMEID=gm.GAMEID AND gd.GDID=p_devid ORDER BY PLAYTIME DESC;

END DEVELOPER_GAME_POPULARITY;
    
END REPORTPKG;
/
DECLARE
    drzave REPORTPKG.tab_country;
BEGIN
    drzave:=REPORTPKG.GET_REPORT();
    REPORTPKG.PRINT_REPORT(drzave);
END;
/
CREATE OR REPLACE PACKAGE RequestPkg IS
    PROCEDURE APPROVEREQUEST(p_name IN VARCHAR2, p_desc IN CLOB, p_year IN INTEGER, p_rule IN CLOB);
END RequestPkg;
/
CREATE OR REPLACE PACKAGE BODY RequestPkg IS
    PROCEDURE APPROVEREQUEST(p_name IN VARCHAR2, p_desc IN CLOB, p_year IN INTEGER, p_rule IN CLOB) IS
        v_broj GAME.GAMEID%TYPE;
    BEGIN
        v_broj:=game_seq.nextval;
        INSERT INTO GAME VALUES (v_broj, p_name, p_desc, 0, p_year, p_rule, 0, 0, 0, 0, 0, '../../games/' || v_broj || '.jpg', '../../icons/' || v_broj || '.png');
    END APPROVEREQUEST;
END RequestPkg;
/

CREATE OR REPLACE TRIGGER REQUESTAPPROVED
AFTER UPDATE OF REQSTATUS
ON REQUEST
FOR EACH ROW
BEGIN
    IF :OLD.REQSTATUS='ACCEPTED' THEN
        RequestPkg.APPROVEREQUEST(:OLD.REQGNAME, :OLD.REQDESC, :OLD.REQGRD, :OLD.REQDR);
    END IF;
END REQUESTAPPROVED;
/
CREATE OR REPLACE TRIGGER REQUESTADD
BEFORE INSERT
ON REQUEST
FOR EACH ROW
DECLARE
    v_postoji INTEGER:=0;
BEGIN
    SELECT count(*) INTO v_postoji FROM GAME WHERE GAMENAME=:NEW.REQGNAME AND GAMERY=:NEW.REQGRD;
    IF v_postoji!=0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'The desired game already exists in database');
    END IF;
END REQUESTADD;
/