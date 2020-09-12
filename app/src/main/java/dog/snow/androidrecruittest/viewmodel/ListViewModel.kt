package dog.snow.androidrecruittest.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputLayout
import dog.snow.androidrecruittest.app.SnowDogApplication
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.room.RoomRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ListViewModel(private val repository: RoomRepository = RoomRepository()): ViewModel() {
    private val disposables = CompositeDisposable()
    val searchLiveData = MutableLiveData<List<RawPhoto>>()

    fun createTextChangeObservable(input: TextInputLayout) {
        val textChangeObservable = Observable.create<String> {emitter ->
            val textWatcher = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) = Unit

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

                override fun onTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                    charSequence?.toString()?.let {
                        emitter.onNext(it)
                    }
                }
            }
            input.editText?.addTextChangedListener(textWatcher)

            emitter.setCancellable {
                input.editText?.removeTextChangedListener(textWatcher)
            }
        }
        val textChangeStream = textChangeObservable.toFlowable(BackpressureStrategy.BUFFER)

        val subscription = textChangeStream
            .observeOn(Schedulers.io())
            .map { repository.findPhotos(title = "%$it%") as List<RawPhoto> }
            .subscribe {
                searchLiveData.postValue(it)
            }

        disposables.add(subscription)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}