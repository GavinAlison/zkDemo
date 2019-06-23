架构图在 resource/masterLeader/subscribe.jpg

## 说明：
-   左边代表Zookeeper集群，右侧代表服务器集群。其中前3个为工作服务器Work Server，绿色为管理服务器Manage Server，
最下面的是控制服务器Control Server。
-   config节点，用于配置管理。Manage Server通过config节点下发配置信息，Work Server可以通过订阅config节点的改变来更新自己的配置。
-   Servers节点，用于服务发现，每个Work Server在启动时都会在Servers节点下创建一个临时节点，Manage Server充当monitor，
通过监听Servers节点的子节点列表的变化来更新自己内存中工作服务器列表的信息。
-   通过Control Server由Command节点作为中介，向Manage Server发送控制指令。Control Server向command节点写入命令信息，
Manage Server订阅command节点的数据改变来监听并执行命令。

## 类对应图
-   WorkServer对应架构图的Work Server；
-   ManageServer对应架构图的Manage Server；
-   ServerConfig用于记录Work Server的配置信息；
-   ServerData用于记录Work Server的基本信息；
-   SubscribeZkClient作为示例程序入口服务站启动Work Server和Manage Server