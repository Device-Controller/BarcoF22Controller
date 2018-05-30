parseURLId(location.href);
let id;
let lampNum;

updateData();

function parseURLId(url) {
    let startIndex = url.indexOf("?") + 1;
    let endIndex = url.length + 1;
    return parseId(url.slice(startIndex, endIndex - 1));

}

function parseId(id) {
    let startIndex = id.indexOf("=") + 1;
    let endIndex = id.length + 1;
    return id.slice(startIndex, endIndex - 1);
}

document.getElementById("muteList").onchange = function () {
    if (this.value == 'mute') {
        mute();
    } else if (this.value == 'unMute') {
        unMute();
    }
};

function mute() {
    fetch('/api/BarcoF22/mute?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => console.log(e));
        }
    })
}

function unMute() {
    fetch('/api/BarcoF22/unMute?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => console.log(e));
        }
    })
}

document.getElementById("imageList").onchange = function () {
    testImage(this.value);
}

function testImage(image) {
    fetch('/api/BarcoF22/testImage?id=' + parseURLId(location.href) + '&image=' + image).then(response => {
        if (response.ok) {
            response.json().then(e => console.log(e));
        }
    })
}

document.querySelector('#contrast-value').addEventListener('keypress', function (e) {
    var key = e.which || e.keyCode;
    if (key === 13) {
        setContrast(document.querySelector('#contrast-value'));
        document.querySelector('#contrast').value = document.querySelector('#contrast-value').value;
    }
});
document.getElementById("muteList").onchange = function () {
    fetch('api/BarkoF22/' + this.value + '?id=' + parseURLId(location.href));
};
document.getElementById("power-list").onchange = function() {
    fetch('api/BarkoF22/power' + this.value + '?id=' + parseURLId(location.href));
    console.log(this.value);
};

function setContrast(number) {
    fetch('/api/BarcoF22/setContrast?id=' + parseURLId(location.href) + '&value=' + number.value).then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("contrast-value").innerHTML = e);
        }
    });
}

function setBrightness(number) {
    fetch('/api/BarcoF22/setBrightness?id=' + parseURLId(location.href) + '&value=' + number.value).then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("brightness-value").innerHTML = e);
        }
    });
}

function setValue(element1, element2) {
    element1.value = document.getElementById(element2).value;
}


function updateData() {
    fetch('/api/BarcoF22/getPower?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => {
                document.getElementById("power-state").value = e;
            });
        }
    });
    fetch('/api/BarcoF22/getMute?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => {
                document.getElementById("mute-state").value = e;
            });
        }
    });

    //GET CONTRAST

    fetch('/api/BarcoF22/getContrast?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => {
                document.getElementById("contrast").value = e;
                document.getElementById("contrast-value").value = e;
            });
        }
    });

//GET POWERSTATE
    fetch('/api/BarcoF22/powerState?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("get-powerstate").innerHTML = e);
        }
    });

//GET BRIGHTNESS
    fetch('/api/BarcoF22/getBrightness?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => {
                document.getElementById("brightness").value = e;
                document.getElementById("brightness-value").value = e;
            });
        }
    });
    fetch('/api/BarcoF22/getTestImage?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => {
                if (e === 0) {
                    document.getElementById("test-image").value = "Off";
                } else {
                    document.getElementById("test-image").value = e;
                }
            });
        }
    });

//GET TEMPERATURE
    fetch('/api/BarcoF22/getThermal?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("temperature").innerHTML = e);
        }
    });

//GET PROJECTOR RUNTIME
    fetch('/api/BarcoF22/getProjectorRuntime?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("projector-runtime").innerHTML = e);
        }
    });

//GET LAMPRUNTIME 1
    fetch('/api/BarcoF22/lampRuntime?id=' + parseURLId(location.href) + '&lampNum=1').then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("lamp1-runtime").innerHTML = e);
        }
    });

//GET LAMPSTATUS 1
    fetch('/api/BarcoF22/lampStatus?id=' + parseURLId(location.href) + '&lampNum=1').then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("lamp1-status").innerHTML = e);
        }
    });
}
