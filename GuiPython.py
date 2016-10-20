from tkinter import *
class Application(Frame):
	"""docstring for Application"""
	def __init__(self,master = None):
		super(Application, self).__init__()
		self.pack()
		self.createWidgets()

	def createWidgets(self):
		self.helloLabel = Label(self,text = 'hell,pythonGUI !')
		self.helloLabel.pack()
		self.quitButton = Button(self,text = 'Quit',command = self.quit)
		self.quitButton.pack()

app = Application()
app.master.title('test')
app.mainloop()