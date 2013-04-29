/**
 *  gcc -o cmd cmd.c -lXtst -lX11
 **/

#include "cmd.h"
#include <errno.h>
#include <X11/extensions/XTest.h>
#include <stdio.h>
#include <X11/keysym.h>

/**
 *  在x,  y坐标处点击鼠标
 **/
int clickAt(int x, int y)
{
	Display *dpy = XOpenDisplay(NULL);
	int ret;

	ret = move_to(x, y);
	if(ret < 0)
		return -1;
	XTestFakeButtonEvent(dpy, 1, 1, 0);
	usleep(10);
	XTestFakeButtonEvent(dpy, 1, 0, 0);
	XCloseDisplay(dpy);
	return 0;
}

int click()
{
	Display *dpy = XOpenDisplay(NULL);
	XTestFakeButtonEvent(dpy, 1, 1, 0);
	usleep(10);
	XTestFakeButtonEvent(dpy, 1, 0, 0);
	XCloseDisplay(dpy);
	return 0;
}

int right_click()
{
	Display *dpy = XOpenDisplay(NULL);
	XTestFakeButtonEvent(dpy, 3, 1, 0);
	usleep(10);
	XTestFakeButtonEvent(dpy, 3, 0, 0);
	XCloseDisplay(dpy);
	return 0;
}

/**cc
 *  获得当前鼠标坐标
 **/
int current_pos(struct position *position)
{
	Display *dpy = XOpenDisplay(NULL);
	XEvent event;

	if(!position)
		return -EINVAL;
	if(!dpy)
		return -ENOMEM;
	/*获得当前的指针信息*/
	XQueryPointer(dpy, RootWindow(dpy, DefaultScreen(dpy)),
      &event.xbutton.root, &event.xbutton.window,
      &event.xbutton.x_root, &event.xbutton.y_root,
      &event.xbutton.x, &event.xbutton.y,
      &event.xbutton.state);
	/*保存坐标*/
	position->x = event.xbutton.x_root;
	position->y = event.xbutton.y_root;
	/*释放资源*/
	XCloseDisplay(dpy);
	return 0;
}

/**
 *  将鼠标移动到x, y处
 **/
int move_to(int x, int y)
{
	Display *dpy = XOpenDisplay(NULL);
	if(!dpy)
		return -ENOMEM;
	/*移到x, y处*/
	XTestFakeMotionEvent(dpy, -1, x, y, 0);
	XCloseDisplay(dpy);
	return 0;
}

/**
 *
 **/
 int move(int x_delta, int y_delta)
 {
 	struct position position;

 	int ret;
 	ret = current_pos(&position);
 	if(ret < 0)
 		return ret;
 	return move_to(position.x + x_delta, position.y + y_delta);
 }

 /*鼠标按下*/
int button_press()
{
	Display *dpy = XOpenDisplay(NULL);
	if(!dpy)
		return -ENOMEM;
	XTestFakeButtonEvent(dpy, 1, 1, 0);
	XCloseDisplay(dpy);
	return 0;
}

 /*鼠标释放*/
int button_release()
{
	Display *dpy = XOpenDisplay(NULL);
	if(!dpy)
		return -ENOMEM;
	XTestFakeButtonEvent(dpy, 1, 0, 0);
	XCloseDisplay(dpy);
	return 0;
}

/**
 * 鼠标拖拽
 **/
int button_drag(int x_delta, int y_delta)
{
	button_press();
	usleep(1000);
	move(x_delta, y_delta);
	//button_release();
	return 0;
}

/**
 *  鼠标拖拽至@x, @y
 **/
int button_drag_to(int x, int y)
{
	button_press();
	usleep(1000);
	move_to(x, y);
	//button_release();
	return 0;
}

/*按键按下*/
int key_press(unsigned int key)
{
	Display *dpy = XOpenDisplay(NULL);
	XTestFakeKeyEvent(dpy, XKeysymToKeycode(dpy, key), 1, 0);
	XCloseDisplay(dpy);
	return 0;
}

/*按键释放*/
int key_release(unsigned int key)
{
	Display *dpy = XOpenDisplay(NULL);
	XTestFakeKeyEvent(dpy, XKeysymToKeycode(dpy, key), 0, 0);
	XCloseDisplay(dpy);
	return 0;
}


#ifdef CMD_DEBUG

int main(int argc, char **argv)
{
	int i ;
	struct position position;
	sleep(2);
	for(i = 33; i < 126; i ++)
	{
		//current_pos(&position);
		//printf("position:%d, %d\n", position.x, position.y);
		//button_press();
		//sleep(2);
		//button_release();
		//right_click();
		//move(50 + i * 10, 20 + 10 * i);
		button_drag_to(100 * i, 100 * i);
		usleep(1000);
		button_release();
		//printf("key_code:%d", i + 10);
		key_press(XK_Left);
		usleep(10);
		key_release(XK_Left);

		sleep(1);
	}
	button_release();
}
#endif