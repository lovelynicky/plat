
/**
互助规则表结构
 */
create table mutual_aid_rule(
  id int(16) PRIMARY KEY COMMENT  '主键id',
  name varchar(1024) COMMENT '名称',
  template_path varchar(1024) COMMENT '模板路径',
  create_time TIMESTAMP COMMENT '创建时间',
  update_time TIMESTAMP COMMENT '更新时间'
)ENGINE=InnoDB;