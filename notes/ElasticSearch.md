### 使用指南
- 官方下载：elasticsearch
https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.6.11.zip
- 官方下载：kibana
https://artifacts.elastic.co/downloads/kibana/kibana-5.6.11-windows-x86.zip
- 官方下载：logstash
https://artifacts.elastic.co/downloads/logstash/logstash-5.6.11.zip


### 配置文件：conf/elasticsearch.yml


### 安装LogStash，使用`logstash-input-jdbc`实现与数据库同步
1. logstash-plugin.bat install logstash-input-jdbc
```
	PS F:\xpndev\elasticsearch56\logstash-5.6.11\logstash-5.6.11\bin> .\logstash-plugin.bat install logstash-input-jdbc
	"warning: ignoring JAVA_TOOL_OPTIONS=$JAVA_TOOL_OPTIONS"
	Validating logstash-input-jdbc
	Installing logstash-input-jdbc
	Installation successful
```

2. logstash-plugin.bat install logstash-output-elasticsearch
```
	PS F:\xpndev\elasticsearch56\logstash-5.6.11\logstash-5.6.11\bin> .\logstash-plugin.bat install logstash-output-elasticsearch
	"warning: ignoring JAVA_TOOL_OPTIONS=$JAVA_TOOL_OPTIONS"
	Validating logstash-output-elasticsearch
	Installing logstash-output-elasticsearch
	Installation successful
```

3. 配置文件（实时同步数据）

PS F:\xpndev\elasticsearch56> .\logstash-5.6.11\logstash-5.6.11\bin\logstash.bat -f .\sync_table.cfg

4. 参考文档
- https://segmentfault.com/a/1190000011784259
