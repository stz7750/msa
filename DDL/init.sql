CREATE TABLE IF NOT EXISTS public.app_user (
  row_id      BIGSERIAL PRIMARY KEY,     -- rowid(serial)
  user_id     VARCHAR(100) NOT NULL UNIQUE, -- 아이디
  user_nm     VARCHAR(100) NOT NULL ,       -- user name
  os 		  VARCHAR(50),                  -- client os
  browser     VARCHAR(50),              -- client brewser
  password    TEXT NOT NULL,             -- 패스워드(해시 저장)
  salt        TEXT NOT NULL,             -- salt (bcrypt면 보통 불필요하지만 요구대로)
  email       VARCHAR(255) UNIQUE,        -- 이메일
  ip          INET,                      -- 아이피 (IPv4/IPv6)
  join_dt     TIMESTAMPTZ NOT NULL DEFAULT NOW() -- 가입일자
);



CREATE TABLE IF NOT EXISTS public.board (
  board_id    BIGSERIAL PRIMARY KEY,          -- 게시글 ID (rowid)
  user_id     VARCHAR(100) NOT NULL,           -- 작성자 아이디
  title       VARCHAR(200) NOT NULL,           -- 제목
  content     TEXT NOT NULL,                   -- 내용
  view_cnt    INTEGER NOT NULL DEFAULT 0,      -- 조회수
  use_yn      CHAR(1) NOT NULL DEFAULT 'Y',    -- 사용 여부 (Y/N)
  reg_ip      INET,                            -- 작성 IP
  created_dt  TIMESTAMPTZ NOT NULL DEFAULT NOW(), -- 작성일
  updated_dt  TIMESTAMPTZ                     -- 수정일
);

CREATE TABLE IF NOT EXISTS public.user_connect (
    user_id VARCHAR(100) NOT NULL,
    url1 TEXT  NULL,
    url2 TEXT NULL,
    regist_id VARCHAR(100) NULL,
    regist_dt date NULL,
    updt_dt date NULL,
    updt_id VARCHAR(100) NULL
    CONSTRAINT user_connect_pk PRIMARY KEY (user_id)
);






CREATE TABLE IF NOT EXISTS public.api_exec_history (
  hist_id        BIGSERIAL PRIMARY KEY,                 -- 이력아이디(serial)

  exec_id        VARCHAR(100) NOT NULL,                 -- 실행 ID(트랜잭션/요청 추적용)
  method         VARCHAR(10)  NOT NULL,                 -- GET/POST/PUT/DELETE...
  url            TEXT         NOT NULL,                 -- 호출 URL

  status         TEXT  NOT NULL DEFAULT 'RUNNING', -- 실행중/성공/실패

  regist_dt      TIMESTAMPTZ  NOT NULL DEFAULT NOW(),   -- 등록일자
  exec_start_dt  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),   -- 실행 시작시간
  exec_end_dt    TIMESTAMPTZ ,                      -- 종료시간(종료 시 업데이트),
  msg			TEXT 
);

