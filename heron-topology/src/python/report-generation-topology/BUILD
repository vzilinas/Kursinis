python_library(
    name = "spouts-bolts-py",
    sources=globs('*.py', exclude=[['report_generation_topology.py']]),
)

python_binary(
    name = "report-generation-topology",
    source='report_generation_topology.py',
    dependencies = [
        ':spouts-bolts-py',
        '3rdparty/python:heronpy',
        '3rdparty/python:six',
	'3rdparty/python:kafka-python'
    ],
)
