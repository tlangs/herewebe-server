/**
 * Created by trevyn on 10/11/14.
 */
App.SoylentView = Backbone.View.extend({

    el: $('#view-main'),

    events: {
        "click #soylentPaginator li > ": "paginatorClicked"
    },

    pages: {
        introTemplate: '',
        day1Template: '',
        day2Template: '',
        day3Template: '',
        day4Template: '',
        day5_6Template: '',
        day7Template: ''
    },


    initialize: function () {
        $.when(
            $.get('templates/soylent/intro.html').done(_.bind(function (response) {
                this.pages.introTemplate = _.template(response);
            }, this)),
            $.get('templates/soylent/day1.html').done(_.bind(function (response) {
                this.pages.day1Template = _.template(response);
            }, this)),
            $.get('templates/soylent/day2.html').done(_.bind(function (response) {
                this.pages.day2Template = _.template(response);
            }, this)),
            $.get('templates/soylent/day3.html').done(_.bind(function (response) {
                this.pages.day3Template = _.template(response);
            }, this)),
            $.get('templates/soylent/day4.html').done(_.bind(function (response) {
                this.pages.day4Template = _.template(response);
            }, this)),
            $.get('templates/soylent/days5-6.html').done(_.bind(function (response) {
                this.pages.day5_6Template = _.template(response);
            }, this)),
            $.get('templates/soylent/day7.html').done(_.bind(function (response) {
                this.pages.day7Template = _.template(response);
            }, this))
        ).done(_.bind(function () {
            }, this));
        return this;
    },

    render: function () {
        this.$el.html(this.template());
        while (this.pages.introTemplate == '' &&
            this.pages.day1Template == '') {
        }
        this.renderByURL();
        this.highlightPaginator();

    },

    paginatorClicked: function (event) {
        var target = $(event.target).parent();
        if (target.hasClass('active') || target.hasClass('disabled')) {
            return
        }
        var pagenum;
        switch ($(event.target).data('page')) {
            case "previous":
                pagenum = this.getParameterByName('page');
                App.router.navigate("soylent?page=" +
                    (parseInt(pagenum) - 1), {trigger: true});
                break;
            case "next":
                pagenum = this.getParameterByName('page');
                App.router.navigate("soylent?page=" +
                    (parseInt(pagenum) + 1), {trigger: true});
                break;
            default:
                App.router.navigate("soylent?page=" + $(event.target).data('page'), {trigger: true});
                break;
        }

    },

    renderPost: function (pagenum) {
        var container = $('#soylentPostContainer');
        switch (parseInt(pagenum)) {
            case 0:
                container.html(this.pages.introTemplate());
                break;
            case 1:
                container.html(this.pages.day1Template());
                break;
            case 2:
                container.html(this.pages.day2Template());
                break;
            case 3:
                container.html(this.pages.day3Template());
                break;
            case 4:
                container.html(this.pages.day4Template());
                break;
            case 5:
                container.html(this.pages.day5_6Template());
                break;
            case 6:
                container.html(this.pages.day7Template());
                break;
            default:
                container.html(this.pages.introTemplate);
                App.router.navigate("soylent?page=0");
        }
    },

    renderByURL: function () {
        this.renderPost(this.getParameterByName("page"));
        $("html, body").scrollTop(0);

    },

    getParameterByName: function (name) {
        if (location.hash.match(/\?.*/) == null) {
            return "";
        }
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(location.hash.match(/\?.*/)[0]);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    },

    highlightPaginator: function () {
        $('#soylentPaginator li a[data-page=' + this.getParameterByName('page') + ']').parent().addClass('active');
        if (this.getParameterByName('page') == 0) {
            $('#soylentPaginator li a[data-page=previous]').parent().addClass('disabled')
        }
        if (this.getParameterByName('page') == Object.keys(this.pages).length - 1) {
            $('#soylentPaginator li a[data-page=next]').parent().addClass('disabled')
        }
    }



});