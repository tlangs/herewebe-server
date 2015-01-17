/**
 * Created by trevyn on 4/19/14.
 */

App.Router = Backbone.Router.extend({

    routes: {
        '': 'homeView',
        'home': 'homeView',
        'about': 'aboutView',
        'work': 'workView',
        'soylent': 'soylentView'
    },

    views: {
        'homeView': new App.HomeView(),
        'aboutView': new App.AboutView(),
        'workView': new App.WorkView(),
        'soylentView': new App.SoylentView()
    },

    initialize: function() {
        $.when(
            $.get('templates/homeTemplate.html').done(_.bind(function (response) {
                this.views.homeView.template = _.template(response);
            }, this)),
            $.get('templates/aboutTemplate.html').done(_.bind(function (response) {
                this.views.aboutView.template = _.template(response);
            }, this)),
            $.get('templates/workTemplate.html').done(_.bind(function (response) {
                this.views.workView.template = _.template(response);
            }, this)),
            $.get('templates/soylent/soylentTemplate.html').done(_.bind(function (response) {
                this.views.soylentView.template = _.template(response);
            }, this))

        ).done(_.bind(function () {
                Backbone.history.start();

            }, this));
        return this;
    },

    homeView: function() {
        clearNavButtons();
        $('#home-nav').addClass('active');
        this.views.homeView.render();
    },

    aboutView: function() {
        clearNavButtons();
        $('#about-nav').addClass('active');
        this.views.aboutView.render();
    },

    workView: function() {
        clearNavButtons();
        $('#work-nav').addClass('active');
        this.views.workView.render();
    },

    soylentView: function() {
        clearNavButtons();
        this.views.soylentView.render();
    }
});

App.router = new App.Router();