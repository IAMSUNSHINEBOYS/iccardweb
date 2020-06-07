(function($, window, document, undefined) {
   canPlayType = function(file) {
        var audioElement = document.createElement('audio');
        return !!(audioElement.canPlayType && audioElement.canPlayType('audio/' + file.split('.').pop().toLowerCase() + ';').replace(/no/, '') );
    };

    $.fn.audioPlayer = function(params) {
        var params = $.extend({
            classPrefix : 'audioplayer',
            strPlay : 'Play',
            strPause : 'Pause',
            strVolume : 'Volume'
        }, params), cssClass = {}, cssClassSub = {
            playPause : 'playpause',
            playing : 'playing',
            stopped : 'stopped',
            time : 'time',
            timeCurrent : 'time-current',
            timeDuration : 'time-duration',
            bar : 'bar',
            barLoaded : 'bar-loaded',
            barPlayed : 'bar-played',
            volume : 'volume',
            volumeButton : 'volume-button',
            volumeAdjust : 'volume-adjust',
            noVolume : 'novolume',
            muted : 'muted',
            mini : 'mini'
        };

        for (var subName in cssClassSub )
        cssClass[subName] = params.classPrefix + '-' + cssClassSub[subName];

        this.each(function() {
            if ($(this).prop('tagName').toLowerCase() != 'audio')
                return false;

            var $this = $(this), audioFile = $this.attr('src'), isAutoPlay = $this.get(0).getAttribute('autoplay'), isAutoPlay = isAutoPlay === '' || isAutoPlay === 'autoplay' ? true : false, isLoop = $this.get(0).getAttribute('loop'), isLoop = isLoop === '' || isLoop === 'loop' ? true : false, isSupport = false;

            if ( typeof audioFile === 'undefined') {
                $this.find('source').each(function() {
                    audioFile = $(this).attr('src');
                    if ( typeof audioFile !== 'undefined' && canPlayType(audioFile)) {
                        isSupport = true;
                        return false;
                    }
                });
            } else if (canPlayType(audioFile))
                isSupport = true;

            var thePlayer = $(( isSupport ? $('<div>').append($this.eq(0).clone()).html() : '<embed src="' + audioFile + '" width="100%" height="40" volume="100" id="audio" autostart="' + isAutoPlay.toString() + '" loop="' + isLoop.toString() + '" />' ) + '<div class="' + cssClass.playPause + '" title="' + params.strPlay + '"></div>'), theAudio = isSupport ? thePlayer.find('audio') : thePlayer.find('embed'), theAudio = theAudio.get(0);

            thePlayer.addClass( isAutoPlay ? cssClass.playing : cssClass.stopped);

            $this.replaceWith(thePlayer);
        });
        return this;
    };
})(jQuery, window, document); 