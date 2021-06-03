package com.finwin.cristal.custmate.home.transfer_main;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.home.transfer_main.action.TransferAction;

import io.reactivex.disposables.CompositeDisposable;
public class TransferViewmodel extends AndroidViewModel {
    public TransferViewmodel(@NonNull Application application) {
        super(application);
        mAction=new MutableLiveData<>();
        disposable=new CompositeDisposable();
        repository=TransferRepository.getInstance();

        repository.setDisposable(disposable);
        repository.setmAction(mAction);

    }

    MutableLiveData<TransferAction> mAction;
    CompositeDisposable disposable;
    TransferRepository repository;

    public MutableLiveData<TransferAction> getmAction() {
        return mAction;
    }

    public void clickBack(View view)
    {
        mAction.setValue(new TransferAction(TransferAction.CLICK_BACK));
    }


    public void clickAccountTransfer(View view)
    {
        mAction.setValue(new TransferAction(TransferAction.ACCOUNT_TRANSFER));
    }


    public void clickAddBeneficiary(View view)
    {
        mAction.setValue(new TransferAction(TransferAction.ADD_BENEFICIARY));
    }

    public void clickNeftImps(View view)
    {
        mAction.setValue(new TransferAction(TransferAction.NEFT_IMPS));
    }


    public void clickRecentTrasfers(View view)
    {
        mAction.setValue(new TransferAction(TransferAction.VIEW_RECENT_TRANSFERS));
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        mAction.setValue(new TransferAction(TransferAction.DEFAULT));
    }
}
