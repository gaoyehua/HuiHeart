package com.yeyu.james.huiheart.event;

import com.yeyu.james.huiheart.entity.MyUser;

/**
 * Created by James on 2016/12/3.
 */

public class LoginEvent {

    public MyUser myUser;

    public LoginEvent(MyUser mUser){
        myUser =mUser;
    }
}
