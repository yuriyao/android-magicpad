/**
 *  模拟鼠标和键盘事件
 **/
#include <X11/extensions/XTest.h>
#include <stdio.h>
#include <X11/keysym.h>
/**
 *  坐标
 **/
struct position
{
	int x;
	int y;
};

/**
 *  在x,  y坐标处点击鼠标
 **/
int clickAt(int x, int y);

/**
 *  获得当前鼠标坐标
 **/
int current_pos(struct position *position);

/**
 *  将鼠标移动到x, y处
 **/
int move_to(int x, int y);

/**
 *  将鼠标移动到@x, @y处,并有n ms的延时
 **/
int move_to_with_delay(int x, int y, int n);

/**
 *  相对于当前位置移动
 **/
int move(int x_delta, int y_delta);

/**
 *  在当前位置点击
 **/
int click();

/**
 *  在当前位置右击
 **/
int right_click();

/**
 *  进行一些原始事件的模拟
 **/
/*鼠标按下*/
int button_press();

/*鼠标释放*/
int button_release();

/**
 * 鼠标拖拽
 **/
int button_drag(int x_delta, int y_delta);

/**
 *  鼠标拖拽至@x, @y
 **/
int button_drag_to(int x, int y);

/*模拟键*/

/*按键按下*/
int key_press(unsigned int key);
/*按键释放*/
int key_release(unsigned int key);

//#define CMD_DEBUG