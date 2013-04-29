#include "serial.h"
#include <termios.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <stdio.h>

static int speeds[] = 
{
	B0,
	B50,
	B75, 
	B110, 
	B134,
	B150,
	B200,
	B300,
	B600,
	B1200,
	B2400,
	B4800,
	B9600,
	B19200,
	B57600,
	B115200
};

/**
 *  打开@number号串口
 *  number  串口的编号
 **/
int open_serial(int number)
{
	char serial_name[20];
	sprintf(serial_name, "/dev/ttyUSB%d", number);
	/*以读写、不控制终端和不关心DCD信号的状态的方式打开*/
	return open(serial_name, O_RDWR | O_NOCTTY | O_NDELAY);
}


/**
 *  设置输入波特率
 **/
void set_ibaud(int fd, enum SERIAL_SPPEED speed)
{
	struct termios options;
	tcgetattr(fd, &options);
	cfsetispeed(&options, speeds[speed]);
	options.c_cflag |= (CLOCAL | CREAD);
	tcsetattr(fd, TCSANOW, &options);
}

/**
 *  设置输出波特率
 **/
void set_obaud(int fd, enum SERIAL_SPPEED speed)
{
	struct termios options;
	tcgetattr(fd, &options);
	cfsetospeed(&options, speeds[speed]);
	options.c_cflag |= (CLOCAL | CREAD);
	tcsetattr(fd, TCSANOW, &options);
}

/**
 *  设置校验方式
 **/
void set_parity(int fd, enum PARITY parity)
{
	struct termios options;
	tcgetattr(fd, &options);
	switch(parity)
	{
	/*无校验*/
	case P_NO:
		options.c_cflag &= ~PARENB;
		options.c_cflag |= CS8;
	/*奇校验*/
	case P_ODD:
		options.c_cflag |= PARENB;
		options.c_cflag |= PARODD;
		options.c_cflag |= CS7;
	/*偶校验*/
	case P_EVEN:
		options.c_cflag |= PARENB;
		options.c_cflag &= ~PARODD;
		options.c_cflag |= CS7;
	default:
		printf("不识别的校验方式\n");
		return;
	}
	options.c_cflag &= ~CSTOPB;
	options.c_cflag &= CSIZE;
	tcsetattr(fd, TCSANOW, &options);
}


#ifdef SERIAL_DEBUG
#include <stdio.h>
#include <stdlib.h>
int main(int argc, char *argv)
{
	int fd = open_serial(0);
	char buffer[10];
	int ret;
	if(fd < 0)
	{
		perror("open");
		exit(-1);
	}
	set_ibaud(fd, SS_B9600);
	for(;;)
	{
		ret = read(fd, buffer, 9);
		if(ret < 0)
		{
			perror("read");
			exit(-1);
		}
		if(ret > 0)
		{
			buffer[ret] = '\0';
			printf("receive %s\n", buffer);
		}
		usleep(1000000);
		
	}
}
#endif