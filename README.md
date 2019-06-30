# JobTimeTracking
Timekeeping at work, regarding the court judgment of the European Court of Justice.

# Concept

**Is a tool to**
       <ul>
          <li>manage profiles</li>
          <li>add manually  times</li>
          <li>caputre breaks</li>
          <li>capture automatically time</li>
          <li>evaluate time</li>
      </ul> 

**Use Case Diagram**
<img src="https://image.prntscr.com/image/am4pn7qARESMg6moEnaaNA.jpg" />  

**State Diagram**
<img src="https://image.prntscr.com/image/Y5JBc5LlQNO0b_AZqb4CRw.jpg" />  

**Structure**
3 Layers: View, Control and Repository
<ul>
       <li><View with fxml files and controller classes</li>
       <li>Control with services for encapsulating the data business methods</li>
       <li>Repository for saving and loading data, using JAXB</li>
</ul>

**Further features**
<ul>
       <li>Evaluation with custom time borders</li>
       <li>List and edit Timetracking entries</li>
       <li>Evaluating Vacation days</li>
       <li>Automatic holidays</li>
       <li>Disable and enable automatic time tracking</li>
</ul>

**[Using Free Icon](https://icons8.de/icons)**
