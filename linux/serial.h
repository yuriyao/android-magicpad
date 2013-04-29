/**
 *  串口通信
 **/

enum SERIAL_SPPEED
{
	SS_B0,
	SS_B50,
	SS_B75, 
	SS_B110, 
	SS_B134,
	SS_B150,
	SS_B200,
	SS_B300,
	SS_B600,
	SS_B1200,
	SS_B2400,
	SS_B4800,
	SS_B9600,
	SS_B19200,
	SS_B57600,
	SS_B115200
};

/*校验方式*/
enum PARITY
{
	/*无校验*/
	P_NO,
	/*奇校验*/
	P_ODD,
	/*偶校验*/
	P_EVEN
};

/**
 *  打开@number号串口
 *  number  串口的编号
 **/
int open_serial(int number);

/**
 *  设置输入波特率
 **/
void set_ibaud(int fd, enum SERIAL_SPPEED speed);

/**
 *  设置输出波特率
 **/
void set_obaud(int fd, enum SERIAL_SPPEED speed);

/**
 *  设置校验方式
 **/
void set_parity(int fd, enum PARITY parity);

#define SERIAL_DEBUG

