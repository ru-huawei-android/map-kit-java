package com.huawei.hms.mapkit.direction.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.huawei.hms.mapkit.direction.model.Direction;
import com.huawei.hms.mapkit.direction.repository.DirectionRepository;
import com.huawei.hms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public class DirectionViewModel extends ViewModel {

    private DirectionRepository directionRepository = new DirectionRepository();

    public LiveData<Direction> getDrivingDirectionLiveData(RequestBody request) {
        return directionRepository.getDrivingDirection(request);
    }

    public LiveData<Direction>  getWalkingDirectionLiveData(RequestBody request) {
        return directionRepository.getWalkingDirection(request);
    }

    public LiveData<Direction>  getBicyclingDirectionLiveData(RequestBody request) {
        return directionRepository.getBicyclingDirection(request);
    }

    public LiveData<Map<Integer, List<LatLng>>> getPolylineLiveData(Direction direction) {
        return directionRepository.getPolyline(direction);
    }
}
