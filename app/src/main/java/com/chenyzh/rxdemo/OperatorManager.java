package com.chenyzh.rxdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class OperatorManager {

    BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create();
    AlertDialog.Builder builder;
    AlertDialog dialog;

    Context context;

    public OperatorManager(final Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
//        withCombineLatest();
        withZip();
//        withMerge();
    }

    private void dialogHandle(String title,String string) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        builder.setTitle(title).setMessage(string).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.show();
    }

    private void withMerge() {
        Observable.merge(behaviorSubject, getOtherCountObs(), getCountObs()).flatMap(new Function<Integer, Observable<String>>() {
            @Override
            public Observable<String> apply(Integer integer) {
                return Observable.just(integer).map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return "订阅的数字：" + integer;
                    }
                });
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) {
                dialogHandle("merge operator",o);
            }
        });
    }

    private void withZip() {
        Observable.zip(behaviorSubject, getOtherCountObs(), getCountObs(), new Function3<Integer, Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2, Integer integer3) throws Exception {
                return integer + "+" + integer2 + "+" + integer3 + " = " + (integer + integer2 + integer3);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                dialogHandle("zip operator",s);
            }
        });
    }

    private void withCombineLatest(){
        Observable.combineLatest(getOtherCountObs(),behaviorSubject, getCountObs(),  new Function3<Integer, Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2, Integer integer3) throws Exception {
                return integer + "+" + integer2 + "+" + integer3 + " = " + (integer + integer2 + integer3);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                dialogHandle("combineLatest operator",s);
            }
        });
    }



    public void combainObs() {
        behaviorSubject.onNext(10);
    }


    public Observable<Integer> getCountObs() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Thread.sleep(5000);
                emitter.onNext(25);
            }
        });
    }

    public Observable<Integer> getOtherCountObs() {
        return Observable.just (9,11);
    }
}
