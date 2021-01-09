package rig.up.Interface;

import java.util.List;

import rig.up.Model.Component;

public interface FirebaseLoadDone {

  //  void onFirebaseLoadSuccess(List<Component> componentList);
    void onFirebaseLoadFailed(String message);
    void onFirebaseLoadMobo(List<Component> componentList);
    void onFirebaseLoadCpu(List<Component> componentList);
    void onFirebaseLoadRam(List<Component> componentList);
    void onFirebaseLoadGpu(List<Component> componentList);
    void onFirebaseLoadStorage(List<Component> componentList);
    void onFirebaseLoadCasing(List<Component> componentList);
    void onFirebaseLoadPsu(List<Component> componentList);



}
