-- Desenvolvedora...: Jaqueline Alves
-- Data..........: 16/02/2018

create sequence SEQ_TBOD_PLANO start with 1 increment by 1;

create table TBOD_PLANO 
(
	cd_plano 			numeric (10) 	not null,
	nome_plano			VARCHAR2(50) 	,
	titulo				VARCHAR2(50) 	,
	cd_tipo_plano		numeric (10) 	,
	valor_mensal		numeric (5,2)	, 
	valor_anual			numeric	(5,2)	,
	ativo				numeric (1)		,
	codigo				varchar2(10)	not null
	
);

alter table TBOD_PLANO
	add constraint "PK_TBOD_PLANO" primary key (cd_plano);
	
alter table TBOD_PLANO
	add constraint "FK_TBOD_PLANO_TIPO" FOREIGN key (cd_tipo_plano)
		references TBOD_TIPO_PLANO (cd_tipo_plano);

----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_TIPO_PLANO start with 1 increment by 1;

create table TBOD_TIPO_PLANO 
(
	cd_tipo_plano 		numeric (10) 	not null,
	descricao			VARCHAR2(50) 	
);

alter table TBOD_TIPO_PLANO
	add constraint "PK_TBOD_TIPO_PLANO" primary key (cd_tipo_plano);
	
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_PLANO_COBERTURA start with 1 increment by 1;

create table TBOD_PLANO_COBERTURA 
(
	cd_plano_cobertura 		numeric (10) 	not null,
	cd_plano 				numeric (10) 	not null,
	cd_cobertura			numeric (10)	not null
);

alter table TBOD_PLANO_COBERTURA
	add constraint "PK_TBOD_PLANO_COBERTURA" primary key (cd_plano_cobertura);
	
alter table TBOD_PLANO_COBERTURA
	add constraint "FK_TBOD_PLANO_COBERTURA_PLANO" FOREIGN key (cd_plano)
		references TBOD_PLANO (cd_plano);
		
alter table TBOD_PLANO_COBERTURA
	add constraint "FK_TBOD_PLANO_COB_COBERTURA" FOREIGN key (cd_cobertura)
		references TBOD_COBERTURA (cd_cobertura);
	
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_COBERTURA start with 1 increment by 1;

create table TBOD_COBERTURA 
(
	cd_cobertura		numeric (10)	not null,
	descricao			VARCHAR2(50) 	not null
);

alter table TBOD_COBERTURA
	add constraint "PK_TBOD_COBERTURA" primary key (cd_cobertura);

--------------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_EMPRESA start with 1 increment by 1;

create table TBOD_EMPRESA 
(
	cd_empresa				numeric (10)	not null,
	razao_social			VARCHAR2(200) 	not null,
	cnpj					varchar2(50)	not null,
	inc_estadual			varchar2(15)	not null,
	ramo_atividade			varchar2(50)	not null,
	nome_fantasia			varchar2(50)	not null,
	representante_legal		varchar2(50)	not null,
	contato_empresa			char	(1)		not null,
	telefone				varchar2(15)	not null,
	celular					varchar2(15)	not null,
	email					varchar2(50)	not null,
	cd_endereco				numeric (10)	not null,
	emp_dcms				varchar2(6)
);

alter table TBOD_EMPRESA
	add constraint "PK_TBOD_EMPRESA" primary key (cd_empresa);

----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_VENDA start with 1 increment by 1;

create table TBOD_VENDA 
(
	cd_venda				numeric (10)	not null,
	cd_empresa				numeric (10)	,
	cd_plano 				numeric (10) 	not null,
	cd_forca_vendas			numeric (10) 	,
	dt_venda				date			not null,
	cd_venda_vida			numeric(10)		,
	cd_status_venda			numeric(10)		,
	tipo_conta				varchar(10)		,
	banco					numeric(10)		,
	agencia					varchar(10)		,
	agencia_dv				varchar(10)		,
	conta					varchar(10)		,
	conta_dv				varchar(10)		,
	tipo_pagamento			varchar(10)		,
	proposta_dcms			varchar(20)
	
);

alter table TBOD_VENDA
	add constraint "PK_TBOD_VENDA" primary key (cd_venda);
	
alter table TBOD_VENDA
	add constraint "FK_TBOD_VENDA_EMPRESA" FOREIGN key (cd_empresa)
		references TBOD_EMPRESA (cd_empresa);
		
alter table TBOD_VENDA
	add constraint "FK_TBOD_VENDA_PLANO" FOREIGN key (cd_plano)
		references TBOD_PLANO (cd_plano);
		
alter table TBOD_VENDA
	add constraint "FK_TBOD_VENDA_FORCA" FOREIGN key (cd_forca_vendas)
		references TBOD_FORCA_VENDA (cd_forca_venda);

alter table TBOD_VENDA
	add constraint "FK_TBOD_VENDA_VENDA_VIDA" FOREIGN key (cd_venda_vida)
		references TBOD_VENDA_VIDA (cd_venda_vida);
	
alter table TBOD_VENDA
	add constraint "FK_TBOD_VENDA_STATUS" FOREIGN key (cd_status_venda)
		references TBOD_STATUS_VENDA (cd_status_venda);
		
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_VENDA_VIDA start with 1 increment by 1;

create table TBOD_VENDA_VIDA 
(
	cd_venda_vida			numeric (10)	not null,
	cd_venda				numeric (10),
	cd_vida	 				numeric (10),
	cd_plano				numeric (10)
);

alter table TBOD_VENDA_VIDA
	add constraint "PK_TBOD_VENDA_VIDA" primary key (cd_venda_vida);
	
alter table TBOD_VENDA_VIDA
	add constraint "FK_TBOD_VENDA_VIDA_VENDA" FOREIGN key (cd_venda)
		references TBOD_VENDA (cd_venda);
		
alter table TBOD_VENDA_VIDA
	add constraint "FK_TBOD_VENDA_VIDA_VIDA" FOREIGN key (cd_vida)
		references TBOD_VIDA (cd_vida);
		
alter table TBOD_VENDA_VIDA
	add constraint "FK_TBOD_VENDA_VIDA_PLANO" FOREIGN key (cd_plano)
		references TBOD_PLANO (cd_plano); --Verificar necessidade
		
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_VIDA start with 1 increment by 1;

create table TBOD_VIDA 
(
	cd_vida			 		numeric (10) 	not null,
	nome					VARCHAR2(100)   not null,
	cpf						VARCHAR2(13),
	sexo					char(1)			not null,
	data_nascimento			date			not null,
	nome_mae				varchar2(50)	not null,
	celular					varchar2(15),
	email					varchar2(50),
	cd_titular				numeric(10),
	cd_endereco				numeric(10),
	pf_pj					char	(1)
);

alter table TBOD_VIDA                     
	add constraint "PK_TBOD_VIDA" primary key (cd_vida);
	
alter table TBOD_VIDA
	add constraint "FK_TBOD_VIDA_VIDA" FOREIGN key (cd_titular)
		references TBOD_VIDA (cd_vida);
	
alter table TBOD_VIDA
	add constraint "FK_TBOD_VIDA_ENDERECO" FOREIGN key (cd_endereco)
		references TBOD_ENDERECO (cd_endereco);
		
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_PLANO_VIDA start with 1 increment by 1;

create table TBOD_PLANO_VIDA 
(
	cd_plano_vida			numeric (10)	not null,
	cd_vida			 		numeric (10) 	not null,
	cd_plano				numeric (10) 	not null
);

alter table TBOD_PLANO_VIDA
	add constraint "PK_TBOD_PLANO_VIDA" primary key (cd_plano_vida);

alter table TBOD_PLANO_VIDA
	add constraint "FK_TBOD_PLANO_VIDA_VIDA" FOREIGN key (cd_vida)
		references TBOD_VIDA (cd_vida);
		
alter table TBOD_PLANO_VIDA
	add constraint "FK_TBOD_PLANO_VIDA_PLANO" FOREIGN key (cd_plano)
		references TBOD_PLANO_COBERTURA (cd_plano); --VERIFICAR
		
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_TIPO_ENDERECO start with 1 increment by 1;

create table TBOD_TIPO_ENDERECO 
(
	cd_tipo_endereco	numeric (10)	not null,
	descricao			VARCHAR2(50) 	not null
);

alter table TBOD_TIPO_ENDERECO
	add constraint "PK_TBOD_TIPO_ENDERECO" primary key (cd_tipo_endereco);
	
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_ENDERECO start with 1 increment by 1;

create table TBOD_ENDERECO 
(
	cd_endereco			numeric (10)	not null,
	logradouro			VARCHAR2(50)	not null,
	cep					varchar2(15)	not null,
	cidade				varchar2(30)	not null,
	complemento			varchar2(100),
	bairro				varchar2(50)	not null,
	uf					varchar2(2)		not null,
	cd_tipo_endereco	numeric (10),
	numero				varchar2(10)
);

alter table TBOD_ENDERECO
	add constraint "PK_TBOD_ENDERECO" primary key (cd_endereco);
	
alter table TBOD_ENDERECO
	add constraint "FK_TBOD_END_TIPO_END" FOREIGN key (cd_tipo_endereco)
		references TBOD_TIPO_ENDERECO (cd_tipo_endereco);

----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_DOCUMENTO_ASSOCIADO start with 1 increment by 1;

create table TBOD_DOCUMENTO_ASSOCIADO 
(
	cd_documento_associado	numeric (10)	not null,
	cd_origem_associado		numeric (10) 	not null,
	cd_associado			numeric (10) 	not null,
	cd_documento			numeric (10)	not null
);

alter table TBOD_DOCUMENTO_ASSOCIADO
	add constraint "PK_TBOD_DOCUMENTO_ASSOCIADO" primary key (cd_documento_associado);
	
alter table TBOD_DOCUMENTO_ASSOCIADO
	add constraint "FK_TBOD_DOC_ASSOCIADO_ORG" FOREIGN key (cd_origem_associado)
		references TBOD_ORIGEM_ASSOCIADO (cd_origem_associado);
	
alter table TBOD_DOCUMENTO_ASSOCIADO
	add constraint "FK_TBOD_DOC_ASSOC_CORR" FOREIGN KEY (cd_associado)
		references TBOD_CORRETORA (cd_corretora);
		
alter table TBOD_DOCUMENTO_ASSOCIADO
	add constraint "FK_TBOD_DOC_ASSOC_VIDA" FOREIGN KEY (cd_associado)
		references TBOD_VIDA (cd_vida);
	
alter table TBOD_DOCUMENTO_ASSOCIADO
	add constraint "FK_TBOD_DOC_ASSOCIADO_DOC" FOREIGN key (cd_documento)
		references TBOD_DOCUMENTO (cd_documento);
		
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_ORIGEM_ASSOCIADO start with 1 increment by 1;

create table TBOD_ORIGEM_ASSOCIADO 
(
	cd_origem_associado		numeric (10)	not null,
	decricao				varchar2(50)	not null
);

alter table TBOD_ORIGEM_ASSOCIADO
	add constraint "PK_TBOD_ORIGEM_ASSOCIADO" primary key (cd_origem_associado);
		
		
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_DOCUMENTO start with 1 increment by 1;

create table TBOD_DOCUMENTO 
(
	cd_documento			numeric (10)	not null,
	cd_tipo_documento		numeric (10) 	not null,
	cd_tipo_arquivo			numeric (10) 	not null,
	nome_arquivo			varchar2(100)	not null,
	base64					varchar2(4000)	not null
);

alter table TBOD_DOCUMENTO
	add constraint "PK_TBOD_DOCUMENTO" primary key (cd_documento);
	
alter table TBOD_DOCUMENTO
	add constraint "FK_TBOD_DOC_TIPO_DOC" FOREIGN key (cd_tipo_documento)
		references TBOD_TIPO_DOCUMENTO (cd_tipo_documento);
		
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_TIPO_DOCUMENTO start with 1 increment by 1;

create table TBOD_TIPO_DOCUMENTO 
(
	cd_tipo_documento	numeric (10)	not null,
	descricao			VARCHAR2(50) 	not null
);

alter table TBOD_TIPO_DOCUMENTO
	add constraint "PK_TBOD_TIPO_DOCUMENTO" primary key (cd_tipo_documento);
	
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_FORCA_VENDA start with 1 increment by 1;

create table TBOD_FORCA_VENDA 
(
	cd_forca_venda				numeric (10)	not null,
	nome						VARCHAR2(255) 	not null,
	celular						varchar2(9)		not null, 
	email						varchar2(80)	not null, 
	cd_corretora				numeric (10)	, 
	cd_status_forca_vendas 		numeric (10)	not null, 
	cpf							varchar2(13)	not null,
	ativo						char(1)			not null,
	data_nascimento				date,
	cargo						varchar2(50),
	departamento				varchar2(50),
	banco						numeric (10),
	agencia						varchar2(10),
	conta						varchar2(10),
	boleto						char    (1),
	cd_login					numeric (10)	
);

alter table TBOD_FORCA_VENDA
	add constraint "PK_TBOD_FORCA_VENDA" primary key (cd_forca_venda);
	
alter table TBOD_FORCA_VENDA
	add constraint "FK_TBOD_FORCA_VENDA_CORR" FOREIGN key (cd_corretora)
		references TBOD_CORRETORA (cd_corretora);
		
alter table TBOD_FORCA_VENDA
	add constraint "FK_TBOD_FORCA_VENDA_STATUS" FOREIGN key (cd_status_forca_vendas)
		references TBOD_STATUS_FORCA_VENDA (cd_status_forca_vendas);
		
alter table TBOD_FORCA_VENDA
	add constraint "FK_TBOD_FORCA_VENDA_LOGIN" FOREIGN key (cd_login)
		references TBOD_LOGIN (cd_login);

----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_STATUS_FORCA_VENDA start with 1 increment by 1;

create table TBOD_STATUS_FORCA_VENDA 
(
	cd_status_forca_vendas		numeric (10)	not null,
	descricao					VARCHAR2(30) 	not null
);

alter table TBOD_STATUS_FORCA_VENDA
	add constraint "PK_TBOD_STATUS_FORCA_VENDA" primary key (cd_status_forca_vendas);
	
	
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_CORRETORA start with 1 increment by 1;

create table TBOD_CORRETORA 
(
	cd_corretora				numeric (10)	not null,
	nome						VARCHAR2(255) 	not null,
	codigo						varchar2(30)	not null,
	cnpj						varchar2(50)	not null,
	cd_endereco					numeric (10)	not null,
	ativo						char    (1)		not null,
	regional					varchar2(50)	not null,
	cpf_responsavel				varchar2(13),
	razao_social				varchar2(255),
	telefone					varchar2(9),
	simples_nacional			char	(1)		not null,
	data_abertura				date			not null,
	status_cnpj					varchar2(100)	not null,
	nome_representante_legal1	varchar2(200),
	nome_representante_legal2	varchar2(200),
	celular						varchar2(9)		not null, 
	email						varchar2(80),
	cnae						varchar2(50),
	cd_login					numeric (10)
);

alter table TBOD_CORRETORA
	add constraint "PK_TBOD_CORRETORA" primary key (cd_corretora);
	
alter table TBOD_CORRETORA
	add constraint "FK_TBOD_CORRETORA_ENDERECO" FOREIGN key (cd_endereco)
		references TBOD_ENDERECO (cd_endereco);
		
alter table TBOD_CORRETORA
	add constraint "FK_TBOD_CORRETORA_LOGIN" FOREIGN key (cd_login)
		references TBOD_LOGIN (cd_login);
		
	
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_LOGIN start with 1 increment by 1;

create table TBOD_LOGIN 
(
	cd_login					numeric (10)	not null,
	cd_tipo_login				numeric (10)	not null,
	senha						VARCHAR2(20) 	not null,
	foto_perfil_b64				varchar2(4000)
);

alter table TBOD_LOGIN
	add constraint "PK_TBOD_LOGIN" primary key (cd_login);
		
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_CORRETORA_BANCO start with 1 increment by 1;

create table TBOD_CORRETORA_BANCO 
(
	cd_corretora_banco			numeric (10)	not null,
	cd_corretora				numeric (10)	not null,
	cd_banco_conta				numeric (10)	not null,
	cnae						varchar2(50)	not null,
	cpf_responsavel_legal1		varchar2(15)	not null,
	cpf_responsavel_legal2		varchar2(15)	
);

alter table TBOD_CORRETORA_BANCO
	add constraint "PK_TBOD_CORRETORA_BANCO" primary key (cd_corretora_banco);
	
alter table TBOD_CORRETORA_BANCO
	add constraint "FK_TBOD_CORR_BANCO_CORR" FOREIGN key (cd_corretora)
		references TBOD_CORRETORA (cd_corretora);
		
alter table TBOD_CORRETORA_BANCO
	add constraint "FK_TBOD_CORRETORA_BANCO_CONTA" FOREIGN key (cd_banco_conta)
		references TBOD_BANCO_CONTA (cd_banco_conta);
		
----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_BANCO_CONTA start with 1 increment by 1;

create table TBOD_BANCO_CONTA 
(
	cd_banco_conta				numeric (10)	not null,
	agencia						varchar2(10)	not null,
	conta						varchar2(10)	not null,
	codigo_banco				numeric (10)	not null
);

alter table TBOD_BANCO_CONTA
	add constraint "PK_TBOD_BANCO_CONTA" primary key (cd_banco_conta);


----------------------------------------------------------------------------------------------------------

create sequence SEQ_TBOD_STATUS_VENDA start with 1 increment by 1;

create table TBOD_STATUS_VENDA 
(
	cd_status_venda				numeric (10)	not null,
	descricao					varchar2(30)
);

alter table TBOD_STATUS_VENDA
	add constraint "PK_TBOD_STATUS_VENDA" primary key (cd_status_venda);
	