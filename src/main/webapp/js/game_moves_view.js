function GameMovesView(template, target) {
  this.template = template;
  this.target = target;
}

GameMovesView.prototype.notify = function(game) {
  var template = $(this.template).html();
  var rendered = Mustache.render(template, {
	//da settare
    items: game.moves,
  });
  $(this.target).html(rendered);
}