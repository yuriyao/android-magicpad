/**
 *
 **/
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include "magic_pad.h"

#include "cmd.h"

#define CHILD_FD 1
#define FATHER_FD 0

#define PORT_NUMBER 38301

void up()
{
	key_press(XK_Up);
	usleep(1000);
	key_release(XK_Up);
}

void down()
{
	key_press(XK_Down);
	usleep(1000);
	key_release(XK_Down);
}

void parse_cmd(char *cmd)
{
	float x;
	float y;
	switch(cmd[0] - '0')
	{
	case DRAG:
		sscanf(cmd + 1, "?%f:%f", &x, &y);
		printf("x:%f, y:%f\n", x, y);
		move((int)x, (int)y);
		break;
	case ZOOM_IN:
		break;
	case ZOOM_OUT:
		break;
	case FORWARD:
		up();
		break;
	case BACKWARD:
		down();
		break;
	case RIGHT:
		break;
	case LEFT:
		break;
	default:
		break;
	}
}

void server()
{
	int client = socket(AF_INET, SOCK_STREAM, 0);
	int ret;
	struct sockaddr_in client_sock;
	char buffer[1024];

	if(client < 0)
	{
		perror("socket");
		exit(-1);
	}

	memset(&client_sock, 0, sizeof(struct sockaddr_in));
	client_sock.sin_family = AF_INET;
	client_sock.sin_port = htons(PORT_NUMBER);
	if(inet_pton(AF_INET, "localhost", &client_sock.sin_addr) < 0)
	{
		perror("inet_pton");
		exit(-1);
	}

	/*连接*/
	if(connect(client, (struct sockaddr*)&client_sock, sizeof(client_sock)) < 0)
	{
		perror("connect");
		exit(-1);
	}
	while((ret = read(client, buffer, 1023)) >= 0)
	{
		if(ret > 0)
		{
			buffer[ret] = '\0';
			printf("buffer:%s\n", buffer);
			parse_cmd(buffer);
		}
	}
	close(client);
	printf("连接断开\n");
}

int main(int argc, char **argv)
{
	pid_t child;
	int fds[2];
	char buffer[1024];
	int ret;

	if(pipe(fds) < 0)
	{
		perror("pipe");
		exit(-1);
	}
	system("./adb forward tcp:38301 tcp:38301");
	server();
	return 0;
}