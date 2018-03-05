var app = ons.bootstrap('my-app', ['onsen']);

app.factory("SharedStateService", function ($filter) {
    return {
        liftId: "",
        deviceId: "",
        imageLift: "images/down.png",
        //liftStatus: "DOWN",
        //itemStatus: "TAKEN",
        event: [{
            datetime: "",
            liftStatus: "DOWN",
            itemStatus: "TAKEN",
            weight: 0
        }],
        item: {"name": "", "weight": 500},
        schedules: []
    };
});

