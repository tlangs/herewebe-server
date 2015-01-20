/**
 * Created by trevyn on 4/19/14.
 */

App.Router = Backbone.Router.extend({

    routes: {
        'soylent': 'soylentView'
    },

    views: {
        'soylentView': new App.SoylentView()
    },

    initialize: function() {
        $.when(
            $.get('resources/templates/soylent/soylentTemplate.html').done(_.bind(function (response) {
                this.views.soylentView.template = _.template(response);
            }, this))

        ).done(_.bind(function () {
                Backbone.history.start();
                this.soylentView();
            }, this));
        return this;
    },

    soylentView: function() {
        clearNavButtons();
        this.views.soylentView.render();
    }
});

App.router = new App.Router();