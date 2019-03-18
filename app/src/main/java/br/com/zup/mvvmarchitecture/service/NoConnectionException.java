package br.com.zup.mvvmarchitecture.service;


import java.io.IOException;

import br.com.zup.mvvmarchitecture.MyApplication;
import br.com.zup.mvvmarchitecture.R;


class NoConnectionException extends IOException {

    @Override
    public String getMessage() {
        return MyApplication.getInstance().getString(R.string.app_name); // FIXME
    }
}
