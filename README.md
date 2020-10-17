# flintium-tag-config-tools
Ignition module to convert UDTs created from PlantPAx AOI OPC drag/drop to the format used by [Flintium](https://github.com/jlbcontrols/Flintium).

This module is only useful for Flintium Project development. It may be refactored to be more generic in the future.

## Features
Adds `system.udtHelper` jython scripting functions to Ignition to automate tag config editing.  
`system.udtHelper.folderize(parentPath)`  
`system.udtHelper.updateOpcPath(parentPath, opcStart)`  
`system.udtHelper.updateOpcServer(parentPath, opcServer)`  
`system.udtHelper.removePrefixes(parentPath,tagGroup,opcPathFilter)`  

## Required Software  
* Ignition v8

## Setup  
* Download the .modl file - navigate to [releases](./releases), click assets, then click on the .modl file.
* Install the module on your Ignition v8 gateway following Ignition's [module installation instructions](https://docs.inductiveautomation.com/display/DOC80/Installing+or+Upgrading+a+Module).
