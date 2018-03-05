var app = ons.bootstrap('my-app', ['onsen']);

app.factory("SharedStateService", function ($filter) {
    return {
        liftId: "",
        deviceId: "",
        imageLift: "images/down.png",
        liftStatus: "DOWN",
        itemStatus: "TAKEN",
        item: {"name": "", "weight": 500},
        schedules: []
    };
});

